# 🚗 Parking Lot System (Java, OOP, JUnit 5)

A simple **Object-Oriented parking facility system** that manages floors, spaces, a registration gate and cars of different sizes. The system assigns parking spaces based on car size and preferred floor, and it supports registering, deregistering, and visual representation of the parking lot state.

---

## ✅ Features
- `SMALL` cars occupy **1 space**
- `LARGE` cars occupy **2 adjacent spaces**
- Preferred floor selection with **fallback search** (closest available floor)
- Unique **ticket ID** generated on entry
- Car removal releases spaces instantly
- **Textual map** of the parking structure
- Fully unit-tested using **JUnit 5**

---

## 🧠 How It Works
| Car Type | Space Requirement |
|----------|------------------|
| `SMALL`  | 1 adjacent free slot |
| `LARGE`  | 2 adjacent free slots (same floor) |

Parking search order:
preferredFloor → preferredFloor - 1 → preferredFloor + 1 → preferredFloor - 2 → preferredFloor + 2 → ...


---

## 🧪 Running Tests
If using IntelliJ/NetBeans/Eclipse → **Right-click `test` folder → Run All Tests**

If using Maven:
```bash
mvn -q -Dtest=parking.* test


📌 Example Output (toString)

S X X X X
L L X X X
X X X X X

S = Small car, L = Large car, X = empty space

📌 UML Diagram
    A full UML diagram is included in uml.puml.

VSCode PlantUML extension

📎 Technologies Used
    Java (OOP, Encapsulation, Enum, Arrays)

    JUnit 5 (unit testing)

    PlantUML (architecture visualization)

📩 Author: Toghrul Hasanli