package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

public class ContactBook {
    private Map<String, Contact> contacts;
    private Gson gson;
    private String filePath;

    public ContactBook(String filePath) {
        this.contacts = new HashMap<>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.filePath = filePath;
        loadContacts();
    }

    public void addContact(String name, String phoneNumber, String email) {
        Contact contact = new Contact(name, phoneNumber, email);
        contacts.put(name, contact);
        saveContacts();
    }

    public void deleteContact(String name) {
        contacts.remove(name);
        saveContacts();
    }

    public void updateContact(String name, String phoneNumber, String email) {
        Contact contact = contacts.get(name);
        if (contact != null) {
            contact.setPhoneNumber(phoneNumber);
            contact.setEmail(email);
            saveContacts();
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void showAllContacts() {
        System.out.println("All Contacts: \n");
        List<Contact> sortedContacts = new ArrayList<>(contacts.values());
        sortedContacts.sort(Comparator.comparing(Contact::getName));
        for (Contact contact : sortedContacts) {
            System.out.println(contact);
        }
    }
    public void searchContact(String name) {
        Contact contact = contacts.get(name);
        if (contact != null) {
            System.out.println("Contact found:");
            System.out.println(contact);
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void exit() {
        saveContacts();
        System.out.println("Exiting contact book. Goodbye!");
        System.exit(0);
    }

    private void loadContacts() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            contacts = gson.fromJson(reader, new TypeToken<Map<String, Contact>>() {}.getType());
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading contacts from file: " + e.getMessage());
        }
    }

    private void saveContacts() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            gson.toJson(contacts, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String filePath = "D:\\Java\\ssss\\src\\main\\java\\org\\example\\contacts.json"; // Задайте шлях до файлу JSON контактів
        ContactBook contactBook = new ContactBook(filePath);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command (add, delete, update, showAll, search, exit):");
            String command = scanner.nextLine();

            if (command.equals("add")) {
                System.out.println("Enter contact name:");
                String name = scanner.nextLine();
                System.out.println("Enter contact phone number:");
                String phoneNumber = scanner.nextLine();
                System.out.println("Enter contact email:");
                String email = scanner.nextLine();
                contactBook.addContact(name, phoneNumber, email);
            } else if (command.equals("delete")) {
                System.out.println("Enter contact name:");
                String name = scanner.nextLine();
                contactBook.deleteContact(name);
            } else if (command.equals("update")) {
                System.out.println("Enter contact name:");
                String name = scanner.nextLine();
                System.out.println("Enter updated phone number:");
                String phoneNumber = scanner.nextLine();
                System.out.println("Enter updated email:");
                String email = scanner.nextLine();
                contactBook.updateContact(name, phoneNumber, email);
            } else if (command.equals("showAll")) {
                contactBook.showAllContacts();
            } else if (command.equals("exit")) {
                contactBook.exit();
            } else if (command.equals("search")) {
                System.out.println("Enter contact name:");
                String name = scanner.nextLine();
                contactBook.searchContact(name);
            }  else {
                System.out.println("Invalid command. Please try again.");
            }
        }
    }
}