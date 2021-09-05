```

sequenceDiagram
participant User
participant Gateway
participant Message Broker
participant Billing Service
participant Order Service
participant Notification Service

User->> Gateway: POST /billing/register {...}
Gateway->>-Message Broker:  publish
activate Message Broker
Note right of Message Broker: RegisterUser
Message Broker -->> Billing Service: consume
deactivate Message Broker
activate Billing Service
Billing Service->>Message Broker: publish
deactivate Billing Service
activate Message Broker
Note right of Message Broker: RegisterUserResponse
Message Broker -->> Gateway: consume
deactivate Message Broker
Gateway -->> User: 201 Created


User->> Gateway: POST /billing/transfer {...}
Gateway->> Message Broker:  publish
activate Message Broker
Note right of Message Broker: BillingTransfer
Message Broker -->> Billing Service: consume
deactivate Message Broker
activate Billing Service
Billing Service->> Message Broker: publish
deactivate Billing Service
activate Message Broker
Note right of Message Broker: BillingTransferResponse
Message Broker -->> Gateway: consume
deactivate Message Broker
Gateway -->> User: 200 OK

User->> Gateway: POST /order/create {...}
Gateway->> Message Broker:  publish
activate Message Broker
Note right of Message Broker: CreateOrder
Message Broker -->> Order Service: consume
deactivate Message Broker
activate Order Service
Order Service->> Message Broker: publish
activate Message Broker
Note right of Message Broker: PaymentOrder
Message Broker -->> Billing Service: consume
deactivate Message Broker
activate Billing Service
Billing Service->> Message Broker: publish
deactivate Billing Service
Note right of Message Broker: PaymentOrderResponse
Message Broker -->> Order Service: consume
deactivate Message Broker
Order Service->>Message Broker: publish
activate Message Broker
deactivate Order Service
Note right of Message Broker: CreateOrderResponse
Message Broker -->> Gateway: consume
Message Broker -->> Notification Service: consume
deactivate Message Broker
Gateway -->> User: 201 Created
activate Notification Service
Notification Service ->> Notification Service: Sending email
deactivate Notification Service

User->> Gateway: POST /billing/withdraw {...}
Gateway->> Message Broker:  publish
activate Message Broker
Note right of Message Broker: BillingWithdraw
Message Broker -->> Billing Service: consume
deactivate Message Broker
activate Billing Service
Billing Service->> Message Broker: publish
deactivate Billing Service
activate Message Broker
Note right of Message Broker: BillingWithdrawResponse
Message Broker -->> Gateway: consume
deactivate Message Broker
Gateway -->> User: 200 OK
```
