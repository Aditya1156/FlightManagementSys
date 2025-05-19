![Screenshot (11)](https://github.com/user-attachments/assets/fe37102e-caff-40e9-9557-d6c025613d62)# ✈️ Flight Management System - Java Swing Desktop Application

## 📌 Overview

The **Flight Management System** is a Java-based desktop application designed to streamline flight-related operations for an airline or travel company. Built using **Java Swing** for the GUI and **MySQL** for database operations, the system offers a range of features to manage flights, passengers, and bookings effectively.

## 🖥️ Features

- **Add New Flights**: Enter flight details such as number, source, destination, duration, etc.
- **Passenger Management**: Add, view, update, and delete passenger information.
- **Flight Booking**: Book flight tickets for passengers and generate unique booking IDs.
- **View Registered Passengers**: Display a complete list of all registered passengers.
- **Search Functionality**: Easily filter and search flights or passengers by specific criteria.
- **Update/Delete Operations**: Modify or remove flight and booking details.
- **User-Friendly Interface**: Built using Java Swing with intuitive menus, buttons, and forms.

## 🛠️ Tech Stack

| Technology | Usage |
|------------|--------|
| Java       | Core functionality and backend logic |
| Java Swing | GUI/Front-end components |
| MySQL      | Backend relational database |
| JDBC       | Connectivity between Java and MySQL |
| Apache NetBeans | IDE used for development and design |

## 🗃️ Database Structure

**Database Name:** `flight_management`  
**Tables:**

- `passenger` (name, nationality, phone, address, aadhar, gender)
- `flights` (flight_id, source, destination, duration, etc.)
- `bookings` (booking_id, passenger_id, flight_id, date)

> Note: Make sure to create the appropriate tables in MySQL and configure JDBC with correct credentials.

## 🚀 How to Run

1. Clone or download this repository.
2. Open the project in **Apache NetBeans IDE**.
3. Set up your MySQL database and update JDBC URL, username, and password in the source code.
4. Build and Run the project using NetBeans.
5. The main dashboard will open with options to manage flights, passengers, and bookings.

## 📷 Screenshots

![Screenshot (7)](https://github.com/user-attachments/assets/5b3bd36e-2ffd-4b36-b798-96568f30e0c4)
![Screenshot (8)](https://github.com/user-attachments/assets/1b0f81df-bd48-4a36-a402-a5e10acbb68b)
![Screenshot (10)](https://github.com/user-attachments/assets/b4e8a436-4f08-4058-8047-9bc068ac01c2)
![Uploading Screenshot (![Screenshot (12)](https://github.com/user-attachments/assets/45882318-23b9-475f-98d4-2bfb85ab5fa9)
11).png…]()
![Screenshot (13)](https://github.com/user-attachments/assets/9ffa496c-3cfa-4bd5-a484-5e1480aa829d)
![Screenshot (14)](https://github.com/user-attachments/assets/fc475257-9d5a-4c7e-9828-8635941076a8)

![Screenshot (9)](https://github.com/user-attachments/assets/284cc37d-0210-4817-bfcb-6146fad1a942)



> _Add screenshots of your application's UI here, if available._

## 📁 Folder Structure

