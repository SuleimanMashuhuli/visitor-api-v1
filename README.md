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

