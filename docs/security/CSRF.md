🛑 What is CSRF (Cross-Site Request Forgery)?
CSRF is an attack where:
A malicious website tricks a logged-in user’s browser into sending an authenticated request to a trusted website, without the user knowing.

The key idea:
The browser automatically sends cookies, even when the request was initiated by another site.

🧠 The ROOT cause of CSRF (very important)
CSRF exists only because of cookies + sessions.
Browsers:
Automatically attach cookies
Do not ask the user
Do not care who initiated the request
Servers:
Trust cookies
Assume “cookie present = authenticated user”
This trust is what attackers exploit.

🎭 Real-World CSRF Attack (Step by Step)
Scenario
You are logged into your bank website
Bank uses session-based authentication
You don’t log out
You visit a malicious website

Step 1️⃣ Normal Login (Legitimate)
You log in to:
https://mybank.com
Server response:
Set-Cookie: JSESSIONID=ABC123
Browser stores:
JSESSIONID=ABC123
Every future request to mybank.com includes:
Cookie: JSESSIONID=ABC123

Step 2️⃣ Malicious Website Appears 😈
You open:
https://evil.com
That site contains hidden HTML:
<form action="https://mybank.com/transfer" method="POST">
  <input type="hidden" name="toAccount" value="attacker" />
  <input type="hidden" name="amount" value="50000" /></form>
<script>document.forms[0].submit();</script>
You never click anything.

Step 3️⃣ Browser Betrays You 😱
The browser sends:
POST https://mybank.com/transfer
Cookie: JSESSIONID=ABC123
⚠️ Browser automatically adds cookie

Step 4️⃣ Bank Server Gets Fooled
Server logic:
✔ Cookie exists✔ Session valid✔ User authenticated
→ Transfer money 
💥 Attack successful
💥 You never approved it
💥 Server thought it was you
🔥 Why this is called “Cross-Site”

Part	Meaning
Cross-Site	Request came from another website
Request	HTTP request sent
Forgery	Request looked legitimate but wasn’t

🧨 Why CSRF is DANGEROUS
No password required
No malware required
No user interaction required
Works silently
Extremely hard for users to detect
That’s why CSRF is a top OWASP vulnerability.

🛡️ How CSRF Protection Works
Idea:
The server should reject requests that didn’t originate from its own site
CSRF Token Mechanism

Step 1️⃣ Server generates a token
CSRF-TOKEN = random string

Step 2️⃣ Server sends it to browser
As hidden form field
Or custom header

Step 3️⃣ Browser sends it back
POST /transfer
Cookie: JSESSIONID=ABC123
X-CSRF-TOKEN: abcxyz123

Step 4️⃣ Server validates
Cookie ✔
Token ✔
→ Accept request
Why attacker fails ❌
Attacker’s site cannot read CSRF token
Browser won’t send it automatically
Request rejected

🔍 Why CSRF DOES NOT affect REST APIs (important)
REST APIs use:
Authorization: Bearer <JWT>
Browser behavior:
Cookies → auto-attached ❌
Authorization headers → NOT auto-attached ✅
So malicious site cannot do this:
Authorization: Bearer <JWT>
Because:
JWT is stored in memory/localStorage
Browser won’t attach it automatically
JavaScript on evil site can’t access it

🚫 CSRF requires cookies
Authentication	CSRF Risk
Session + Cookie	❌ HIGH
JWT + Header	✅ NONE

🧠 Why we disable CSRF in Spring REST APIs
Spring Security default:
Enables CSRF
Assumes session-based MVC app
But your app is:
REST
Stateless
Token-based
So we do:
csrf(csrf -> csrf.disable())
This is:
✅ Correct
✅ Recommended
✅ Industry standard
⚠️ When NOT to disable CSRF
DO NOT disable CSRF if:
You use form login
You use cookies for auth
You build MVC apps (Thymeleaf, JSP)
🧪 Spring Security Behavior Summary
App Type	CSRF
MVC Web App	ENABLE
REST API	DISABLE
JWT-based	DISABLE
Microservices	DISABLE

🧭 Why this matters for Microservices
Microservices:
No browser sessions
No sticky sessions
Stateless auth
Token-based communication
CSRF has no place here.

🧠 Final Mental Model (remember this)
CSRF attacks abuse browser trust in cookies
REST APIs don’t trust browsers — they trust tokens
 