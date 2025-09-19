# java_Tag_Reader_programme
Java Spring Boot app that connects to a UHF RFID reader over TCP/IP using Clou UHF G3Lib SDK, registers tags, scans in real-time with async callbacks, and logs reports to MySQL with a clean web UI

RFID Spring Boot UHF Reader is a full-stack Java Spring Boot application that connects to a LAN RFID reader via TCP/IP using the Clou UHF G3Lib SDK (.jar), provides a web UI for Tag Registration and Tag Scan, and persists data and scan history in MySQL for reporting and export. It supports multiple SDK package variants (IAsynchronousMessage with Tag6C or Protocol.Tag_Model) to avoid import conflicts across different vendor SDK builds.

Key features

TCP/IP reader connect/disconnect using SDK; resilient startup/shutdown wiring.

Real-time tag capture via IAsynchronousMessage; immediate UI display if tag exists, or a “No Data Found, Please Register Tag” notice.

Tag Registration form with 6 fields (Tag ID, Name, Phone, Unit No, Rank, Other), with uniqueness checks and validations.

Scan log persistence (tag and timestamp) and a Report page with filtering and CSV export.

Clean Spring Boot + Thymeleaf UI, MySQL via Spring Data JPA, SDK JARs included under lib/ and wired in pom.xml.

Tech stack

Spring Boot (Web, Thymeleaf, Data JPA), MySQL, SLF4J, Clou UHF G3Lib SDK (.jar).

Why this repo

Combines end-to-end RFID flows (register, scan, report) with a production-friendly structure and explicit handling for Clou SDK import differences to minimize “class not found” issues.

Suggested topics (GitHub)

spring-boot, rfid, uhf, clou-uhf, g3lib, tcp, iot, mysql, thymeleaf, java

If a one-liner is preferred for GitHub’s short description field, use:

UHF RFID Spring Boot app with TCP/IP reader connect (Clou G3Lib), async tag scan to UI, tag registration, and MySQL reports.
