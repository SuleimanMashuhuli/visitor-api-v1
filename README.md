    <------------------------------ === SPRINGBOOT === ------------------------------>


    <------------------------------ STEPS TO TACKLE SPRINGBOOT PROJECTS ------------------------------>

            Phase 0 — Make the Entities                                                                                                            
            Phase 1 — Repositories                                                                                                                                              
            Phase 2 — DTOs + MapStruct mappers                                                                                                                             
            Phase 3 — Services                                                                                                                          
            Phase 4 — Business Loogic                                                                                                                                
            Phase 5 — Controllers, security, etc...                                                                                                   
            Phase 6 — Config & dependencies  


    <------------------------------ === SERVICES === ------------------------------>

            BranchService
                └── Manage branches
    
            RoleService
                └── Manage roles + assign permissions
                └── Read/manage system permissions
                
            UserService
                └── Manage VMS users + roles + branch

            VisitorService
                └── Manage visitor information

            VisitService
                └── Manage visit lifecycle      

            NotificationService
                └── Create/send/track notifications

            AuditLogService
                └── Record and retrieve audit events


    <------------------------------ STEPS TO TACKLE SPRINGBOOT PROJECTS ------------------------------>

            Entities
            ↓
            Repositories
            ↓
            DTOs
            ↓
            Mappers
            ↓
            Services
            ↓
            Business Logic
            ↓
            Controllers
            ↓
            Security
            ↓
            Testing
            ↓
            Integration
            ↓
            Deployment



    <------------------------------ FOLLOW THW STEPS ------------------------------>

            Create DTOs for requests and responses
            Create Mappers between Entity ↔ DTO
            Create Service interfaces
            Create Service implementations
            Add business rules for visitors and visits
            Create Controllers / REST endpoints
            Add global exception handling
            Add Bean Validation
            Configure Spring Security
            Implement authentication
            Implement RBAC using roles and permissions
            Implement branch-based authorization
            Implement Visitor CRUD
            Implement Visit scheduling
            Implement Visit approval/rejection
            Implement Check-in
            Implement Check-out
            Implement Cancel visit
            Implement No-show handling
            Integrate institution employee/host lookup
            Implement email notifications
            Implement notification failure/retry handling
            Implement audit logging

            Implement visitor search and filtering
            Implement visit history
            Implement active visitors/occupancy queries
            Implement dashboard statistics
            Add pagination and sorting
            Add Swagger/OpenAPI
            Create Flyway/Liquibase database migrations
            Add database indexes and constraints

            Add unit tests
            Add repository tests
            Add service tests
            Add controller/API tests
            Add security tests
            Add integration tests with Testcontainers
            Configure dev / test / prod profiles
            Add Actuator health checks
            Add application logging
            Add metrics/monitoring
            Create Dockerfile
            Create Docker Compose for development
            Configure environment variables/secrets
            Configure CORS
            Configure reverse proxy / API gateway
            Connect the React frontend
            Test the complete visitor lifecycle
            Perform security testing
            Perform UAT
            Prepare production database migrations
            Deploy to the private cloud
            Configure backup/recovery
            Configure monitoring and alerts
            Prepare SOPs and admin/security-team training
            Go live
            Monitor, maintain, and release future improvements

==============================================================
1. Visit.java — add the token field

Add after private String hostName;:
    private String approvalToken;

2. VisitRepository.java — add the token lookup

    Optional<Visit> findByApprovalToken(String approvalToken);
(import java.util.Optional at the top if it's not already there.)

3. VisitService.java — add two methods

    VisitResponseDto approveByToken(String token);
    VisitResponseDto rejectByToken(String token);

4. NotificationService.java — add one method

    NotificationResponseDto createApprovalRequest(Integer visitId);

5. NotificationServiceImpl.java — generate the token + links

Add imports:
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

Add a field + the method:
    @Value("${app.base-url:http://localhost:8094}")
    private String baseUrl;

    @Override
    public NotificationResponseDto createApprovalRequest(Integer visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new EntityNotFoundException("Visit not found with id: " + visitId));

        String token = UUID.randomUUID().toString();
        visit.setApprovalToken(token);
        visitRepository.save(visit);

        String approveLink = baseUrl + "/api/v1/visits/approve?token=" + token;
        String rejectLink  = baseUrl + "/api/v1/visits/reject?token=" + token;
        String message = "A visit has been registered for you.\n\n"
                + "Approve: " + approveLink + "\n"
                + "Reject:  " + rejectLink;

        Notification notification = Notification.builder()
                .recipientEmail(visit.getHostEmail())
                .subject("Visitor approval request")
                .message(message)
                .notificationType("APPROVAL_REQUEST")
                .status(NotificationStatus.PENDING)
                .visit(visit)
                .build();

        return systemMapper.toResponseDto(notificationRepository.save(notification));
    }

6. VisitServiceImpl.java — wire it together

Add the import + field:
import com.ecobank.visitor.services.NotificationService;
// ...
    private final NotificationService notificationService;

Replace createVisit with:
    @Override
    public VisitResponseDto createVisit(VisitRequestDto visitRequestDto) {
        Visit visit = visitMapper.toEntity(visitRequestDto);
        visit.setStatus(VisitStatus.PENDING);
        Visit saved = visitRepository.save(visit);

        var approval = notificationService.createApprovalRequest(saved.getId());
        notificationService.sendNotification(approval.getId());

        return visitMapper.toResponseDto(saved);
    }

Add the two token methods (anywhere in the class):
    @Override
    public VisitResponseDto approveByToken(String token) {
        Visit visit = visitRepository.findByApprovalToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Invalid or expired approval link"));
        if (visit.getStatus() != VisitStatus.PENDING) {
            throw new IllegalStateException("Visit is not awaiting approval");
        }
        visit.setStatus(VisitStatus.APPROVED);
        visit.setApprovalToken(null);
        return visitMapper.toResponseDto(visitRepository.save(visit));
    }

    @Override
    public VisitResponseDto rejectByToken(String token) {
        Visit visit = visitRepository.findByApprovalToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Invalid or expired approval link"));
        if (visit.getStatus() != VisitStatus.PENDING) {
            throw new IllegalStateException("Visit is not awaiting approval");
        }
        visit.setStatus(VisitStatus.REJECTED);
        visit.setApprovalToken(null);
        return visitMapper.toResponseDto(visitRepository.save(visit));
    }

(Setting approvalToken = null after use makes the link single-use — a second click gets "Invalid or expired.")

7. New file — controllers/VisitApprovalController.java

cat > src/main/java/com/abcbank/visitor/controllers/VisitApprovalController.java << 'EOF'
package com.ecobank.visitor.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecobank.visitor.domain.dto.VisitResponseDto;
import com.ecobank.visitor.services.VisitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
public class VisitApprovalController {

    private final VisitService visitService;

    @GetMapping("/approve")
    public ResponseEntity<VisitResponseDto> approve(@RequestParam String token) {
        return ResponseEntity.ok(visitService.approveByToken(token));
    }

    @GetMapping("/reject")
    public ResponseEntity<VisitResponseDto> reject(@RequestParam String token) {
        return ResponseEntity.ok(visitService.rejectByToken(token));
    }
}
EOF

This is a separate controller with no @RolesAllowed, so it's public. (Literal /approve wins over /{id} in VisitController, so no routing conflict.)

8. SecurityConfig.java — permit the two token links
