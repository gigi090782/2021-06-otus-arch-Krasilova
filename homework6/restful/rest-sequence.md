```
sequenceDiagram

participant User
participant Billing Service
participant Order Service
participant Notification Service
	
User->>Billing Service: POST /billing/register
activate Billing Service
Billing Service-->>User: 201 CREATED
deactivate Billing Service

User->>Billing Service: POST /billing/transfer
activate Billing Service
Billing Service->>Billing Service: Processing transfer
Billing Service-->>User: 200 OK
deactivate Billing Service

activate Order Service
User->>Order Service: POST /order/create {price}
Order Service->>Billing Service: POST /billing/paymentOrder
activate Billing Service
Billing Service->>Billing Service: Payment processing
Billing Service->>Order Service: 200 OK
deactivate Billing Service

Order Service->>Notification Service: POST /notification/sendEmail {template_id, email, context}
activate Notification Service
Notification Service-->>Order Service: 202 Accepted
Notification Service->>Notification Service: Sending email
deactivate Notification Service
Order Service-->>User: 201 Created
deactivate Order Service


User->>Billing Service: POST /billing/withdraw
activate Billing Service
Billing Service->>Billing Service: Processing withdraw
Billing Service-->>User: 200 OK
deactivate Billing Service
```
