
**User Service**
----------------

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

-   **`User` 类**：表示用户实体，映射到数据库中的 `users` 表。

    -   **关键变量**：
        -   `Long id`：用户的唯一标识符，主键。
        -   `String username`：用户名，必须唯一。
        -   `String password`：加密后的用户密码。
        -   `String email`：用户的电子邮箱，必须唯一。
        -   `Role role`：用户的角色，使用 `Role` 枚举表示。
        -   `boolean enabled`：用户账户是否启用。
-   **`Role` 枚举**：定义了系统中的用户角色。

    -   **枚举值**：
        -   `PASSENGER`：乘客
        -   `DRIVER`：司机
        -   `ADMIN`：管理员

**对应需求**：

-   **用户管理和角色划分**：通过 `User` 类和 `Role` 枚举实现对用户信息的存储和角色的区分。

#### **2\. 数据传输对象（DTO）**

-   **`UserRegistrationDto` 类**：用于封装用户注册时提交的数据。
    -   **关键变量**：
        -   `String username`：用户名。
        -   `String password`：密码。
        -   `String email`：电子邮箱。
        -   `String role`：用户角色，以字符串形式提交。

**对应需求**：

-   **用户注册**：通过 `UserRegistrationDto` 接收用户注册信息，确保数据传输的安全性和完整性。

#### **3\. 仓库层（Repository）**

-   **`UserRepository` 接口**：继承自 `JpaRepository`，用于对 `User` 实体进行数据库操作。
    -   **关键方法**：
        -   `Optional<User> findByUsername(String username)`：根据用户名查找用户。
        -   `boolean existsByUsername(String username)`：检查用户名是否已存在。
        -   `boolean existsByEmail(String email)`：检查电子邮箱是否已注册。

**对应需求**：

-   **数据持久化**：通过 `UserRepository` 对用户数据进行增删改查操作。

#### **4\. 服务层（Service）**

-   **`UserService` 接口**：定义了用户服务的抽象方法。

    -   **关键方法**：
        -   `void registerUser(UserRegistrationDto registrationDto)`：注册新用户。
-   **`UserServiceImpl` 类**：实现了 `UserService` 接口，包含业务逻辑。

    -   **关键方法**：
        -   `registerUser(UserRegistrationDto registrationDto)`：实现用户注册逻辑，包括检查用户名和邮箱是否已存在，密码加密等。

**对应需求**：

-   **业务逻辑实现**：处理用户注册的业务逻辑，确保数据的正确性和安全性。

#### **5\. 控制器层（Controller）**

-   **`UserController` 类**：处理与用户相关的 HTTP 请求。
    -   **关键方法**：
        -   `ResponseEntity<String> registerUser(UserRegistrationDto registrationDto)`：处理用户注册请求。
        -   `ResponseEntity<JwtAuthenticationResponse> authenticateUser(LoginRequest loginRequest)`：处理用户登录请求，返回 JWT 令牌。

**对应需求**：

-   **API 接口**：提供用户注册和登录的 RESTful API。

#### **6\. 安全配置（Security）**

-   **`SecurityConfig` 类**：配置 Spring Security。

    -   **关键配置**：
        -   禁用 CSRF。
        -   配置请求权限，开放 `/users/register` 和 `/users/login`，保护其他接口。
        -   配置 JWT 认证过滤器。
-   **`JwtTokenProvider` 类**：生成和验证 JWT 令牌。

    -   **关键方法**：
        -   `String generateToken(Authentication authentication)`：生成 JWT 令牌。
        -   `Long getUserIdFromJWT(String token)`：从令牌中解析用户 ID。
        -   `boolean validateToken(String authToken)`：验证令牌的有效性。
-   **`JwtAuthenticationFilter` 类**：JWT 认证过滤器，拦截请求并进行身份验证。

-   **`UserDetailsImpl` 类**：实现了 `UserDetails` 接口，提供用户的基本信息给 Spring Security。

    -   **关键变量**：
        -   `Long id`：用户 ID。
        -   `String username`：用户名。
        -   `String password`：密码。
        -   `Collection<? extends GrantedAuthority> authorities`：用户权限。
-   **`UserDetailsServiceImpl` 类**：实现了 `UserDetailsService` 接口，用于加载用户信息。

    -   **关键方法**：
        -   `UserDetailsImpl loadUserByUsername(String username)`：根据用户名加载用户。
        -   `UserDetailsImpl loadUserById(Long id)`：根据用户 ID 加载用户。

**对应需求**：

-   **身份验证和授权**：通过 JWT 实现无状态的身份验证，保护受限的 API 接口。

#### **7\. 异常处理**

-   **`GlobalExceptionHandler` 类**：全局异常处理器，统一处理应用程序中的异常。
    -   **关键方法**：
        -   `handleRuntimeException(RuntimeException ex, WebRequest request)`：处理运行时异常。
        -   `handleValidationException(MethodArgumentNotValidException ex)`：处理参数验证异常。
        -   `handleGlobalException(Exception ex, WebRequest request)`：处理其他异常。

**对应需求**：

-   **错误处理和反馈**：为客户端提供一致和友好的错误信息。

### **实现的要求与对应**

-   **用户注册和登录功能**：通过 `UserController`、`UserService`、`UserServiceImpl` 等类实现。
-   **密码加密存储**：在 `UserServiceImpl` 中使用 `PasswordEncoder` 对密码进行加密。
-   **JWT 认证和授权**：通过 `JwtTokenProvider`、`JwtAuthenticationFilter`、`SecurityConfig` 等类实现。
-   **角色权限控制**：在 `UserDetailsImpl` 中设置用户的角色权限，用于 Spring Security 进行访问控制。
-   **全局异常处理**：通过 `GlobalExceptionHandler` 实现统一的异常处理机制。
