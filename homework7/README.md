Идемпотетность и коммутативность
Реализация идемпотентности для метода создания заказа в API.

Используемый паттерн - "Идемпотентность для коллекций".

Для реализации идемпотентности в запросе передается версия коллекции в ETag


Запустить minikube 
```
minikube start --vm-driver= hyperkit\
 --vm=true
--cpus=4 --memory=8g \
--cni=flannel \
--kubernetes-version="v1.19.0" 
```

Включить ingress
```
minikube addons  enable ingress
```

запустить skaffold 

```
 skaffold run
 ```

Запускаем тесты с помощью newman. 

```
newman run  postman/order.postman_collection.json
```

```
newman

Order service

→ Получаем текущую версию
  GET http://arch.homework/version [200 OK, 166B, 73ms]

→ Создание заказа
  POST http://arch.homework/order [201 Created, 288B, 18ms]

→ Получаем текущую версию
  GET http://arch.homework/version [200 OK, 166B, 10ms]

→ Создание нового заказа
  POST http://arch.homework/order [201 Created, 290B, 55ms]

→ Попытка создания повторного заказа
  POST http://arch.homework/order [400 Bad Request, 103B, 10ms]

→ Получение списка заказов
  GET http://arch.homework/orders [200 OK, 1.53KB, 34ms]

┌─────────────────────────┬───────────────────┬──────────────────┐
│                         │          executed │           failed │
├─────────────────────────┼───────────────────┼──────────────────┤
│              iterations │                 1 │                0 │
├─────────────────────────┼───────────────────┼──────────────────┤
│                requests │                 6 │                0 │
├─────────────────────────┼───────────────────┼──────────────────┤
│            test-scripts │                 6 │                0 │
├─────────────────────────┼───────────────────┼──────────────────┤
│      prerequest-scripts │                 0 │                0 │
├─────────────────────────┼───────────────────┼──────────────────┤
│              assertions │                 0 │                0 │
├─────────────────────────┴───────────────────┴──────────────────┤
│ total run duration: 332ms                                      │
├────────────────────────────────────────────────────────────────┤
│ total data received: 1.59KB (approx)                           │
├────────────────────────────────────────────────────────────────┤
│ average response time: 33ms [min: 10ms, max: 73ms, s.d.: 23ms] │
└────────────────────────────────────────────────────────────────┘

 ```

