import { ContactService } from '../contact-component/contact.service';
import { Contact } from '../contact-component/contact';
import { Address } from '../contact-component/address';
import { Communication } from '../contact-component/communication';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as moment from 'moment';

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
    this.contact.Address = [{
      id: 0,
      type: '',
      number: 0,
      street: '',
      unit: '',
      city: '',
      state: '',
      zipcode: ''
    }];
    this.contact.Communication = [{
      id: 0,
      type: '',
      value: '',
      preferred: false
    }];
  }

  ngOnInit() {
    
  }

  newContact(): void {
    this.submitted = false;
    this.contact = new Contact();
    this.contact.Address = [{
      id: 0,
      type: '',
      number: 0,
      street: '',
      unit: '',
      city: '',
      state: '',
      zipcode: ''
    }];
    this.contact.Communication = new Array<Communication>();
  }

  save() {
    var date = moment(this.contact.Identification.dob).format('MM/DD/yyyy');
    this.contact.Identification.dob = date;
    console.log(this.contact);
    this.contactService
    .createContact(this.contact).subscribe(data => {
      console.log(data)
      this.contact = new Contact();
      this.gotoList();
    }, 
    error => console.log(error));
  }

  onSubmit() {
    this.save();
    this.submitted = true;
  }

  gotoList() {
    this.router.navigate(['/contacts']);
  }

  addAddress() {

    let add = {
      id: 0,
      type: '',
      number: 0,
      street: '',
      unit: '',
      city: '',
      state: '',
      zipcode: ''
    };
    this.contact.Address.push(add);
  }

  addComm() {

    let comm = {
      id: 0,
      type: '',
      value: '',
      preferred: false
    }

    this.contact.Communication.push(comm);
  }

  public myDatePickerOptions: any = {
    // Your options
  };
}
