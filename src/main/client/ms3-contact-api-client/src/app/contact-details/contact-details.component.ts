import { Contact } from '../contact-component/contact';
import { Component, OnInit, Input } from '@angular/core';
import { ContactService } from '../contact-component/contact.service';
import { ContactListComponent } from '../contact-list/contact-list.component';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-contact-details',
  templateUrl: './contact-details.component.html',
  styleUrls: ['./contact-details.component.css']
})
export class ContactDetailsComponent implements OnInit {

  id: number;
  contact: Contact;

  constructor(private route: ActivatedRoute,private router: Router,
    private contactService: ContactService) {

    this.contact = new Contact();

  }

  ngOnInit() {
    this.contact = new Contact();

    this.id = this.route.snapshot.params['id'];
    
    this.contactService.getContact(this.id)
      .subscribe(data => {
        console.log(data)
        this.contact = data;
      }, error => console.log(error));
  }

  list(){
    this.router.navigate(['contacts']);
  }

  getGenderView(gender: string) {
    if (gender == "F") {
      return "Female";
    } else {
      return "Male";
    }
  }

  deleteContact(id: number) {
    this.contactService.deleteContact(id)
      .subscribe(
        data => {
          console.log(data);
        },
        error => console.log(error));
    this.list();
  }

  updateContact(id: number) {
    this.router.navigate(['contactupdate', id]);
  }
}
