✅ What is Redis?

Redis = Remote Dictionary Server

👉 Redis is a very fast in-memory data store used as:

Database

Cache

Message broker

Session store

🧠 Key Idea (Core Concept)

Redis stores data in RAM instead of disk, so it is extremely fast.

Typical DB read: ~5–50 ms
Redis read: ~0.1–1 ms

✅ What Type of Database is Redis?

Redis is a:

NoSQL key-value store

In-memory data structure store

🧱 Data Structures Supported by Redis

Unlike simple key-value DBs, Redis supports advanced data structures:

Data Type	Example Use
String	Cache values
List	Queues
Set	Unique items
Sorted Set	Leaderboards
Hash	User objects
Bitmap	Analytics
HyperLogLog	Count unique users
Stream	Message queues
✅ Example Redis Data
SET user:101 "Abhishek"
GET user:101

🚀 Why Redis is So Fast?
Traditional DB (MySQL/Postgres)

Reads from disk (slow)

Complex queries

ACID transactions

Redis

Stores everything in RAM

Simple operations

Optional disk persistence

👉 10x–1000x faster than SQL DB

✅ Real-World Use Cases of Redis
1️⃣ Caching (Most Common)
Example: E-commerce Product API

Without Redis:

API → MySQL → Response


With Redis:

API → Redis (cache) → Response (fast)
If not found → DB → Redis store


👉 Reduces DB load massively.

2️⃣ Session Storage (Used with JWT & Auth)
Example: Login System

Store session or refresh tokens:

session:user123 → token_data


Used by:

Facebook

Netflix

Amazon

3️⃣ Rate Limiting (Prevent API Abuse)
Example:
IP:1.2.3.4 → request_count


Block if >100 requests/min.

Used by APIs like:

Stripe

Cloudflare

AWS API Gateway

4️⃣ Message Queue / Pub-Sub
Example:

Chat apps

Notification systems

Redis can publish and subscribe messages.

5️⃣ Leaderboards (Gaming)

Redis Sorted Set:

player1 → 1200 points
player2 → 1500 points


Get top players instantly.

6️⃣ Real-Time Analytics

Count live users, trending topics, etc.

🧠 Redis vs Traditional Database
Feature	Redis	MySQL/Postgres
Storage	RAM	Disk
Speed	⚡ Extremely fast	Slower
Query	Simple	Complex SQL
Persistence	Optional	Always
Use case	Cache, sessions	Core data

👉 Redis is NOT a replacement for relational DB.

💾 Does Redis Lose Data on Restart?
❌ By default: Yes (RAM lost)
✅ But Redis supports persistence:
1️⃣ RDB Snapshotting

Periodic disk snapshot

2️⃣ AOF (Append Only File)

Logs every write command

🔐 Redis in JWT Authentication (Real Example)
Refresh Token Storage
SET refresh:user123 "token123" EX 7d


If user logs out:

DEL refresh:user123


👉 Token revoked instantly.

🧠 Redis in System Design (Interview Gold)
Example: High Traffic Website
User → API → Redis Cache → DB


Redis reduces DB load by 90%+.

⚡ Redis vs Memcached
Feature	Redis	Memcached
Data types	Rich	Simple
Persistence	Yes	No
Pub/Sub	Yes	No
Speed	Very fast	Very fast
🧪 Example Redis Code (Java Spring Boot)
redisTemplate.opsForValue().set("user:101", "Abhishek");
String name = redisTemplate.opsForValue().get("user:101");