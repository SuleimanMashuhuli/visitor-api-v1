    <------------------------------ === SPRINGBOOT === ------------------------------>

Visitor API Management System for ABC Bank PLC

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
