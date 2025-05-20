# Control-Flow Debugging: PrintTokens.java

This project performs control-flow testing on a fault-seeded Java program (`PrintTokens.java`). It demonstrates how to:

1. Analyze program structure using Control Flow Graphs (CFGs).
2. Design and execute test cases derived from control-flow paths.
3. Identify, record, and correct implementation faults.
4. Generate code coverage reports for unit and end-to-end testing.

---

## 🧩 Project Breakdown

### 1. Test Case Preparation

* **Control Flow Graphs**
  A drawned version of CFGs for each method in `PrintTokens.java`, highlighting all branches and loops.

* **Test Path Enumeration**
  A List of all feasible paths through each CFG, covering:

  * Normal execution paths
  * Exception and boundary cases

* **Test Case Derivation**
  For each path, define input values and the expected output I used (Oracle). Capture expected token sequences and error messages.

### 2. Testing & Debugging

* **Test Implementation**
  I wrote JUnit test methods for each derived test case in `PrintTokensTest.java`. Which used assertions to compare actual vs. expected output.

* **Execution & Monitoring**
  Run tests via your IDE or command line. Observe correction.


### 3. Reporting

* **Coverage Reports**

  * **Unit Testing Report**: HTML report generated via JaCoCo plugin, showing method and branch coverage.
  * **End-to-End Testing Report**: HTML coverage report for full program execution, ensuring all token flows are exercised.

---

## ⚙️ Environment Setup

1. **Java SE 16**
   Download and install from Oracle’s website (account required). Other Java 16+ versions may work.

2. **Eclipse IDE (recommended)**

   * **JUnit**: Add JUnit 5 to your project’s build path:

     1. Right-click project → **Build Path > Configure Build Path**.
     2. **Libraries** tab → **Add Library** → **JUnit** → **Next**.
     3. Choose **JUnit 5** → **Finish** → **Apply and Close**.
   * **JaCoCo**: Install via Eclipse Marketplace for coverage reports.

````

---

## 🚀 Running Tests & Generating Reports

### Unit Tests

```bash
# In terminal (using Maven or Gradle), or via Eclipse Run Configurations:
mvn test
# or
gradle test
````

Results appear in `target/surefire-reports/` or `build/reports/tests/`.

### Coverage Reports

1. **Unit Coverage**

In this case, all report and cases have be complete.
* Please unzip file for Code Coverage Reports for EndToEnd or UnitTest.
* Inside the folder you'll find  `index.html`. Click and a report will be demonstrated.

---

## 📄 Deliverables

* **`PrintTokens.java`**: Fault-seeded source code.
* **`PrintTokensTest.java`**: JUnit test suite.
* **Coverage Reports**: `unit-coverage/index.html`, `e2e-coverage/index.html`.

