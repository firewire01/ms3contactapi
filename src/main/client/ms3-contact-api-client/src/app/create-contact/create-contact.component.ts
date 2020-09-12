import { ContactService } from '../contact-component/contact.service';
import { Contact } from '../contact-component/contact';
import { Address } from '../contact-component/address';
import { Communication } from '../contact-component/communication';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { Observable, BehaviorSubject } from 'rxjs';

export interface Gender {
  value: string;
  viewValue: string;
}

export interface Option {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-create-contact',
  templateUrl: './create-contact.component.html',
  styleUrls: ['./create-contact.component.css']
})
export class CreateContactComponent implements OnInit {

  contact: Contact = new Contact();
  submitted = false;
  addresses: Address[] = [];
  comms: Communication[] = [];
  add : Address = new Address();
  com : Communication = new Communication();

  genders: Gender[] = [
    { value: 'M', viewValue: 'Male' },
    { value: 'F', viewValue: 'Female' }
  ];

  options: Option[] = [
    { value: 'true', viewValue: 'True' },
    { value: 'false', viewValue: 'False' }
  ];

  constructor(private contactService: ContactService,
    private router: Router) {
    this.contact.Identification = {
      id: 0,
      firstName: '',
      lastName: '',
      dob: '',
      gender: '',
      title: ''
    }
  }

  ngOnInit() {

  }

  newContact(): void {
    this.submitted = false;
    this.contact = new Contact();
  }

  save() {
    var date = moment(this.contact.Identification.dob).format('MM/DD/yyyy');
    this.contact.Identification.dob = date;

    this.contact.Address = this.addresses;
    this.contact.Communication = this.comms;

    //check if there is a valid address in the input fields
    if(this.add.type && this.add.street && this.add.city){
            this.contact.Address.push(this.add);
    }

    //check if there is a valid communication in the input fields
    if(this.com.type && this.com.value){
       this.contact.Communication.push(this.com);
    }

    //set preferred value to default to false
    for (let i = 0; i < this.contact.Communication.length; i++) {
              let preferred = this.contact.Communication[i].preferred;
              if (preferred == null || preferred == undefined) {
                this.contact.Communication[i].preferred = false;
              }
     }

    console.log(this.contact);
    this.contactService
    .createContact(this.contact).subscribe(data => {
      console.log(data)
      this.contact = new Contact();
      this.submitted = true;
      this.gotoList();
    },
    error => {console.log(error);
              alert(error.message);
              this.submitted = false;});
  }

  onSubmit() {
    this.save();
  }

  addAddress() {
    this.addresses.push(this.add);
    this.add = new Address();
  }

  addComm() {
    this.comms.push(this.com);
    this.com = new Communication();
  }

  deleteAdd(index){
    this.addresses.splice(index, 1);
  }

  deleteComm(index){
      this.comms.splice(index, 1);
  }

  public myDatePickerOptions: any = {
    // this is create the date picker
  };

  gotoList() {
    this.router.navigate(['/contacts']);
  }

}
