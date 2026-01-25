✅ What is JWT in REST APIs (Real Meaning)

JWT (JSON Web Token) is a self-contained, signed token used for stateless authentication and authorization in REST APIs.

In REST, the server must not store session state.
JWT allows the server to verify user identity without storing sessions.

✅ Why JWT is Needed in REST APIs (Real Production Problem)
❌ Traditional Session-Based Auth Problem

In classic web apps:

Browser → Server (Session ID)
Server → Session DB / Redis


Issues:

Server must store millions of sessions

Load balancers need sticky sessions

Microservices cannot share session state easily

Horizontal scaling becomes complex

✅ JWT Solves This

JWT stores user identity inside the token itself.
Every service can verify token without calling session DB.

✅ JWT Structure (Technical)

JWT format:

HEADER.PAYLOAD.SIGNATURE

Example JWT (decoded):
1️⃣ Header
{
"alg": "RS256",
"typ": "JWT"
}

2️⃣ Payload (Claims)
{
"sub": "user123",
"email": "abhishek@example.com",
"roles": ["ADMIN"],
"iat": 1700000000,
"exp": 1700000900,
"iss": "auth.mycompany.com"
}

3️⃣ Signature
RSASHA256(
base64(header) + "." + base64(payload),
privateKey
)


👉 Prevents tampering.

✅ Real World REST API Authentication Flow with JWT
🧑‍💻 Example: E-commerce System (Amazon-like)
Step 1: User Login
POST /api/auth/login
{
"username": "abhishek",
"password": "Pass@123"
}

Step 2: Auth Server Verifies Credentials

Checks DB

Hashes password

Validates user

Step 3: Auth Server Issues Tokens
{
"access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
"refresh_token": "dGhpcy1pcy1sb25nLXRva2Vu"
}

Step 4: Client Calls Protected REST APIs
GET /api/orders
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...

Step 5: API Gateway / Microservice Verifies JWT

Verify signature using public key

Check expiry

Extract roles

Allow / deny request

👉 No DB call needed.

✅ How JWT Works in Microservices Architecture
Typical Production Architecture
Client → API Gateway → Order Service
→ Payment Service
→ Inventory Service

Token Flow:

Auth Service signs JWT using private key

API Gateway & microservices verify using public key

No session sharing required

✅ Authorization with JWT (Real Use Case)
Payload:
{
"sub": "user123",
"roles": ["ADMIN"]
}

In Spring Boot:
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/users")
public List<User> getUsers() { }


👉 Role-based access control without DB lookup.

✅ Real Production Example: Google / AWS
Google OAuth JWT Example

When you login with Google:

Google issues ID Token (JWT)

Your backend verifies Google public keys

No password shared with your server

AWS Cognito / Azure AD / Okta

Issue JWT tokens

Microservices trust identity provider

Zero session state

✅ JWT vs Session in REST APIs (Production Comparison)
Feature	Session	JWT
Server State	Stored	Stateless
Scalability	Hard	Easy
Microservices	Difficult	Native
DB lookup per request	Yes	No
Revocation	Easy	Hard
Token size	Small	Larger
✅ JWT Token Types in Real Systems
1️⃣ Access Token

Short-lived (5–15 min)

Used for API calls

2️⃣ Refresh Token

Long-lived (days/months)

Stored in DB or Redis

Used to issue new access tokens

✅ Real Industry Security Architecture
Google / Banking Systems Pattern
Token	Storage	Reason
Access Token	Memory / Header	Avoid CSRF
Refresh Token	HTTP-only Cookie	Protect from XSS
CSRF Token	Header	Protect refresh
✅ How JWT is Verified in REST API (Internals)
Verification Steps:

Decode header & payload

Verify signature using secret/public key

Check exp (expiry)

Check iss (issuer)

Check aud (audience)

Extract claims

Example Java Verification (Spring Security)
Jwt jwt = jwtDecoder.decode(token);
String user = jwt.getSubject();
List<String> roles = jwt.getClaimAsStringList("roles");

✅ Why JWT is Perfect for REST APIs

REST principles:

Stateless

Cacheable

Layered system

Uniform interface

👉 JWT matches stateless constraint perfectly.

⚠️ Real World JWT Problems (Important)
1️⃣ Token Revocation Problem

Once issued, cannot be easily revoked.

Solution:

Short expiry

Token blacklist (Redis)

2️⃣ Token Size Overhead

JWT ~1–2 KB per request → affects bandwidth.

3️⃣ Security Risks

XSS → steals token from localStorage

CSRF → when stored in cookies

✅ JWT vs OAuth vs OpenID Connect (Real Meaning)
Term	Meaning
JWT	Token format
OAuth 2.0	Authorization protocol
OpenID Connect	Authentication layer on OAuth
SSO	Uses OIDC + JWT