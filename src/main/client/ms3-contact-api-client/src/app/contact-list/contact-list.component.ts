import { Observable } from "rxjs";
import { ContactService } from "./../contact-component/contact.service";
import { Contact } from "./../contact-component/contact";
import { Component, OnInit } from "@angular/core";
import { Router } from '@angular/router';

@Component({
  selector: "app-contact-list",
  templateUrl: "./contact-list.component.html",
  styleUrls: ["./contact-list.component.css"]
})
export class ContactListComponent implements OnInit {
  contacts: Observable<Contact[]>;

  constructor(private contactService: ContactService,
    private router: Router) {}

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.contacts = this.contactService.getContactList();
  }

  deleteContact(id: number) {
    this.contactService.deleteContact(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  contactDetails(id: number){
    this.router.navigate(['contactdetails', id]);
  }

  updateContact(id: number){
    this.router.navigate(['contactupdate', id]);
  }
}
