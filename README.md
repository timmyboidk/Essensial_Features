**Order Service**
-----------------

### **技术栈**

-   **编程语言**：Java 17
-   **框架**：Spring Boot 3.x
-   **安全**：Spring Security
-   **持久层**：Spring Data JPA
-   **数据库**：MySQL
-   **身份验证**：JWT（JSON Web Token）
-   **依赖管理**：Maven
-   **其他工具**：Lombok（用于简化代码）

### **关键类、变量和方法**

#### **1\. 模型层（Model）**

-   **`Order` 类**：表示订单实体，映射到数据库中的 `orders` 表。

    -   **关键变量**：
        -   `Long id`：订单的唯一标识符，主键。
        -   `OrderStatus status`：订单状态，使用 `OrderStatus` 枚举表示。
        -   `Passenger passenger`：关联的乘客。
        -   `Driver driver`：关联的司机。
        -   `double startLatitude`、`startLongitude`：起始位置坐标。
        -   `double endLatitude`、`endLongitude`：结束位置坐标。
        -   `LocalDateTime orderTime`：下单时间。
        -   `double estimatedCost`：预估费用。
        -   `double estimatedDistance`：预估距离。
        -   `double estimatedDuration`：预估时长。
        -   `VehicleType vehicleType`：车辆类型。
        -   `ServiceType serviceType`：服务类型。
        -   `PaymentMethod paymentMethod`：支付方式。
-   **`OrderStatus` 枚举**：定义订单的状态。

    -   **枚举值**：
        -   `PENDING`：待处理
        -   `ACCEPTED`：已接受
        -   `IN_PROGRESS`：进行中
        -   `COMPLETED`：已完成
        -   `CANCELED`：已取消
-   **`Passenger` 类**：表示乘客实体。

    -   **关键变量**：
        -   `Long id`：乘客 ID。
        -   `String name`：乘客姓名。
-   **`Driver` 类**：表示司机实体。

    -   **关键变量**：
        -   `Long id`：司机 ID。
        -   `String name`：司机姓名。
        -   `VehicleType vehicleType`：车辆类型。
        -   `boolean available`：司机是否可用。
-   **其他枚举类型**：

    -   **`VehicleType`**：车辆类型（`ECONOMY`、`STANDARD`、`PREMIUM`）
    -   **`ServiceType`**：服务类型（`NORMAL`、`EXPRESS`、`LUXURY`）
    -   **`PaymentMethod`**：支付方式（`CASH`、`CREDIT_CARD`、`ONLINE_PAYMENT`）

**对应需求**：

-   **订单管理和状态跟踪**：通过 `Order` 类及相关枚举实现订单的创建、状态更新和管理。

#### **2\. 数据传输对象（DTO）**

-   **`OrderCreateDto` 类**：用于封装创建订单时提交的数据。

    -   **关键变量**：
        -   `Long passengerId`：乘客 ID。
        -   `LocationDto startLocation`：起始位置。
        -   `LocationDto endLocation`：结束位置。
        -   `VehicleType vehicleType`：车辆类型。
        -   `ServiceType serviceType`：服务类型。
        -   `PaymentMethod paymentMethod`：支付方式。
-   **`LocationDto` 类**：表示地理位置的数据传输对象。

    -   **关键变量**：
        -   `Double latitude`：纬度。
        -   `Double longitude`：经度。
-   **`OrderResponseDto` 类**：用于返回订单信息给客户端。

    -   **关键变量**：
        -   `Long orderId`：订单 ID。
        -   `OrderStatus status`：订单状态。
        -   `String passengerName`：乘客姓名。
        -   `String driverName`：司机姓名。
        -   `double estimatedCost`：预估费用。
        -   `double estimatedDistance`：预估距离。
        -   `double estimatedDuration`：预估时长。

**对应需求**：

-   **订单创建和信息返回**：通过 DTO 在客户端和服务端之间传递订单相关的数据。

#### **3\. 仓库层（Repository）**

-   **`OrderRepository` 接口**：继承自 `JpaRepository`，用于对 `Order` 实体进行数据库操作。

-   **`PassengerRepository` 接口**：用于对 `Passenger` 实体进行数据库操作。

-   **`DriverRepository` 接口**：用于对 `Driver` 实体进行数据库操作。

    -   **关键方法**：
        -   `Optional<Driver> findFirstByVehicleTypeAndAvailableTrue(VehicleType vehicleType)`：查找可用的指定车辆类型的司机。

**对应需求**：

-   **数据持久化**：通过仓库层对订单、乘客和司机的数据进行增删改查操作。

#### **4\. 服务层（Service）**

-   **`OrderService` 接口**：定义了订单服务的抽象方法。

    -   **关键方法**：
        -   `OrderResponseDto createOrder(OrderCreateDto orderCreateDto)`：创建新订单。
        -   `void acceptOrder(Long orderId, Long driverId)`：司机接受订单。
        -   `OrderResponseDto getOrderDetails(Long orderId)`：获取订单详情。
        -   `void cancelOrder(Long orderId)`：取消订单。
-   **`OrderServiceImpl` 类**：实现了 `OrderService` 接口，包含订单相关的业务逻辑。

    -   **关键方法**：
        -   `createOrder(OrderCreateDto orderCreateDto)`：实现订单创建逻辑，包括分配司机、计算预估费用和距离等。
        -   `acceptOrder(Long orderId, Long driverId)`：实现司机接受订单的逻辑，更新订单状态和司机状态。
        -   `getOrderDetails(Long orderId)`：获取订单的详细信息。
        -   `cancelOrder(Long orderId)`：取消订单并更新相关状态。

**对应需求**：

-   **订单处理和业务逻辑实现**：处理订单的创建、接受、查询和取消等业务逻辑。

#### **5\. 控制器层（Controller）**

-   **`OrderController` 类**：处理与订单相关的 HTTP 请求。
    -   **关键方法**：
        -   `ResponseEntity<OrderResponseDto> createOrder(OrderCreateDto orderCreateDto)`：处理创建订单的请求。
        -   其他与订单操作相关的接口方法。

**对应需求**：

-   **API 接口**：提供订单创建和管理的 RESTful API。

#### **6\. 安全配置（Security）**

-   **`SecurityConfig` 类**：配置 Spring Security。

    -   **关键配置**：
        -   禁用 CSRF。
        -   配置请求权限，保护 `/orders/**` 路径下的接口。
        -   配置 JWT 认证过滤器。
-   **`JwtTokenProvider` 类**：生成和验证 JWT 令牌（与 `user_service` 中的类似）。

-   **`JwtAuthenticationFilter` 类**：JWT 认证过滤器，拦截请求并进行身份验证。

-   **`OrderDetailsImpl` 类**：实现了 `UserDetails` 接口，提供用户的基本信息给 Spring Security。

-   **`OrderDetailServiceImpl` 类**：实现了 `UserDetailsService` 接口，用于加载用户信息。

**对应需求**：

-   **身份验证和授权**：通过 JWT 实现无状态的身份验证，保护订单相关的 API 接口。

#### **7\. 工具类（Utility）**

-   **`DistanceCalculator` 类**：用于计算两点之间的距离。
    -   **关键方法**：
        -   `double calculateDistance(double lat1, double lon1, double lat2, double lon2)`：计算地球上两点之间的距离。

**对应需求**：

-   **预估费用和距离计算**：在创建订单时，计算预估的行驶距离和费用。

#### **8\. 异常处理**

-   **`GlobalExceptionHandler` 类**：全局异常处理器，统一处理应用程序中的异常（与 `user_service` 中的类似）。

**对应需求**：

-   **错误处理和反馈**：为客户端提供一致和友好的错误信息。

### **实现的要求与对应**

-   **订单创建和分配司机**：通过 `OrderServiceImpl.createOrder` 方法，实现了订单的创建和可用司机的分配。
-   **预估费用和距离计算**：在 `createOrder` 方法中，使用 `DistanceCalculator` 计算预估的行驶距离和费用。
-   **订单状态管理**：使用 `OrderStatus` 枚举和相应的方法，管理订单的状态更新（接受、取消等）。
-   **身份验证和授权**：通过 JWT 认证和授权，保护订单相关的接口，确保只有经过认证的用户才能访问。
-   **全局异常处理**：通过 `GlobalExceptionHandler` 提供统一的错误处理。


**示例请求和响应**
-----------

### **1\. 创建订单**

-   **请求**：`POST /orders/create`
-   **请求体**：


`{
  "passengerId": 1,
  "startLocation": {
    "latitude": 30.0,
    "longitude": 120.0
  },
  "endLocation": {
    "latitude": 31.0,
    "longitude": 121.0
  },
  "vehicleType": "STANDARD",
  "serviceType": "NORMAL",
  "paymentMethod": "CREDIT_CARD"
}`

-   **响应**：

`{
  "orderId": 100,
  "status": "PENDING",
  "passengerName": "Test Passenger",
  "driverName": "Test Driver",
  "estimatedCost": 100.0,
  "estimatedDistance": 200.0,
  "estimatedDuration": 60.0
}`

### **2\. 获取订单详情**

-   **请求**：`GET /orders/100`
-   **响应**：


`{
  "orderId": 100,
  "status": "PENDING",
  "passengerName": "Test Passenger",
  "driverName": "Test Driver",
  "estimatedCost": 100.0,
  "estimatedDistance": 200.0,
  "estimatedDuration": 60.0
}`
