package com.smart.controller;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    //Method for Adding common data
    @ModelAttribute
    public void addCommonData(Model m, Principal p) {
        String username = p.getName();
        User user = this.userRepository.getUserByUserName(username);
        m.addAttribute("user", user);
    }

    //Dashboard Home
    @GetMapping("/index")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("title", "User Dashboard");
        return "user_dashboard";
    }

    //Add Contact
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "add_contact_form";
    }

    //Processing add contact form
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Principal principal, HttpSession session) {
        try {
            String name = principal.getName();
            User user = this.userRepository.getUserByUserName(name);
            //Processing and uploading file
            if (file.isEmpty()) {
                System.out.println("File is EMpty");
                contact.setImage("BG.png");
            } else {
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is Uploaded");
            }
            contact.setUser(user);
            user.getContacts().add(contact);
            this.userRepository.save(user);
            System.out.println("Added to database");
            session.setAttribute("message", new Message("Your Contact is added!!!", "success"));
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            session.setAttribute("message", new Message("Something went wrong!!! Try again...", "danger"));
            e.printStackTrace();
        }
        return "add_contact_form";
    }

    //Show All contacts
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model m, Principal principal) {
        m.addAttribute("title", "Show User Contacts");
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        Pageable pageable = PageRequest.of(page, 3);
        Page<Contact> contacts = this.contactRepository.findContactByUser(user.getId(), pageable);
        m.addAttribute("contacts", contacts);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPages", contacts.getTotalPages());
        return "show_contacts";
    }

    //To show a particular contact
    @GetMapping("/{cid}/contact")
    public String showContactDetail(@PathVariable("cid") Integer cid, Model model, Principal principal) {
        Optional<Contact> contactOptional = this.contactRepository.findById(cid);
        Contact contact = contactOptional.get();
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        if (user.getId() == contact.getUser().getId()) {
            model.addAttribute("contact", contact);
        }
        return "contact_detail";
    }

    //To delete a contact
    @GetMapping("/delete/cid")
    @Transactional
    public String deleteContact(@PathVariable("cid") Integer cid, Model model, HttpSession session, Principal principal) {
        Contact contact = this.contactRepository.findById(cid).get();
        User user = this.userRepository.getUserByUserName(principal.getName());
        user.getContacts().remove(contact);
        this.userRepository.save(user);
        session.setAttribute("message", new Message("Contact deleted succefully!!", "success"));
        return "redirect:/user/show-contacts/0";
    }

    //To open update form handler
    @PostMapping("/update-contact/{cid}")
    public String updateForm(@PathVariable("cid") Integer cid, Model model) {
        model.addAttribute("title", "Update Contact");
        Contact contact = this.contactRepository.findById(cid).get();
        model.addAttribute("contact", contact);
        return "update_form";
    }

    //Update Contact Handler
    @RequestMapping(value = "/process-update", method = RequestMethod.POST)
    public String updateHandler(@ModelAttribute Contact contact, Model model, HttpSession session, Principal principal) {
        try {
            User user = this.userRepository.getUserByUserName(principal.getName());
            contact.setUser(user);
            this.contactRepository.save(contact);
            session.setAttribute("message", new Message("Contact is Updated!!", "success"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/"+contact.getCid()+"/contact";
    }
}