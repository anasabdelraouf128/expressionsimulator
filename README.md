# 🧮 Expression Simulator

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Data Structures](https://img.shields.io/badge/Data_Structures-007396?style=for-the-badge&logo=data-datacamp&logoColor=white)
![Academic Project](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)

> A comprehensive Java-based data structures project designed to process mathematical expressions in infix notation, converting and evaluating them while managing the data through custom-built structures[cite: 1].

---

## ✨ Features

* 🔢 **Infix Processing**: Accepts mathematical expressions in infix notation[cite: 1].
* ➖ **Complex Values**: Supports negative numbers, decimal numbers, and combinations such as `-3.13`[cite: 1].
* ✖️ **Operators & Parentheses**: Handles parentheses and standard mathematical operators: `+`, `-`, `*`, `/`, `%`, and `^`[cite: 1].
* 🛡️ **Robust Error Handling**: Detects invalid cases, including empty input, invalid expressions (missing operators or operands), and division by zero[cite: 1].
* 💬 **Clear Feedback**: Displays clear and meaningful error messages for all edge cases[cite: 1].
* 📁 **File I/O Integration**: Automatically parses input expressions from an input file and saves the formatted output to a separate text file.

---

## 🏗️ Data Structures Implemented

| Structure | Application |
| :--- | :--- |
| 🚶 **Queues** | Stores the initial expression as individual tokens and queues the resulting postfix expression for output[cite: 1]. |
| 🥞 **Stacks** | Handles operators and parentheses according to precedence rules during the infix-to-postfix conversion, and evaluates the final postfix expression[cite: 1]. |
| 🌳 **Binary Search Tree** | Stores the elements of the infix expression and performs tree traversals[cite: 1]. The system outputs the Preorder, Inorder, and Postorder traversals[cite: 1]. |
| #️⃣ **Hash Table** | Parses the expression to extract all numbers, storing them in a hash table of a user-defined size[cite: 1]. Resolves collisions using linear probing, quadratic probing, double hashing with $h_{2}(x)=7-x\%\text{size}$, and separate chaining via a linked list[cite: 1]. |

---

## 📤 Output Format

For a valid expression, the system outputs the following in exact order[cite: 1]:
```text
Queue: [initial tokens]
Postfix: [converted expression]
Result: [computed value]
BST Traversals:
Preorder: [...]
Inorder: [...]
Postorder: [...]
