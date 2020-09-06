import { Component, OnInit } from '@angular/core';
import { Contact } from '../contact-component/contact';
import { ActivatedRoute, Router } from '@angular/router';
import { ContactService } from '../contact-component/contact.service';
import { Address } from '../contact-component/address';
import { Communication } from '../contact-component/communication';
import * as moment from 'moment';
import { Gender } from '../create-contact/create-contact.component';
import { Option } from '../create-contact/create-contact.component';
/*import { Observable } from "rxjs/Observable";
import { Subject } from "rxjs/Subject";
import { List } from 'immutable';
import { asObservable } from "./asObservable";
import { BehaviorSubject } from "rxjs/Rx";*/
import { Observable, BehaviorSubject } from 'rxjs';
import { List } from 'immutable';

@Component({
  selector: 'app-update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.css']
})
export class UpdateContactComponent implements OnInit {

  id: number;
  contact: Contact;
  addresses: BehaviorSubject<Address[]>;
  comms: BehaviorSubject<Communication[]>;

  genders: Gender[] = [
    { value: 'M', viewValue: 'Male' },
    { value: 'F', viewValue: 'Female' }
  ];

  options: Option[] = [
    { value: 'false', viewValue: 'False' },
    { value: 'true', viewValue: 'True' }
   
  ];


  constructor(private route: ActivatedRoute,private router: Router,
    private contactService: ContactService) {

  }

  ngOnInit() {
    this.contact = new Contact();

    this.id = this.route.snapshot.params['id'];

    this.contactService.getContact(this.id)
      .subscribe(data => {
        console.log(data)
        this.contact = data;
        this.contact.Identification.dob =
          moment(this.contact.Identification.dob).utc().format();

        for (let i = 0; i < this.contact.Communication.length; i++) {
          let preferred = this.contact.Communication[i].preferred;
          if (preferred == null || preferred == undefined) {
            this.contact.Communication[i].preferred = false;
          }
        }

        this.addresses = new BehaviorSubject(this.contact.Address);
        this.comms = new BehaviorSubject(this.contact.Communication);

      }, error => console.log(error));
  }

  updateContact() {
    var date = moment(this.contact.Identification.dob).format('MM/DD/yyyy');
    this.contact.Identification.dob = date;
    this.contact.Address = this.addresses.getValue();
    this.contact.Communication = this.comms.getValue();

    this.contactService.updateContact(this.contact)
      .subscribe(data => {
        console.log(data);
        this.contact = new Contact();
        this.gotoList();
      }, error => console.log(error));
  }

  onSubmit() {
    this.updateContact();    
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

    this.addresses.next(this.addresses.value.concat(add));
  }

  addComm() {

    let comm = {
      id: 0,
      type: '',
      value: '',
      preferred: false
    };

    this.comms.next(this.comms.value.concat(comm));
  }

  public getAddresses(): Observable<Address[]> {
    return this.addresses.asObservable();
  }

  public getComms(): Observable<Communication[]> {
    return this.comms.asObservable();
  }

  gotoList() {
    this.router.navigate(['/contacts']);
  }

  public myDatePickerOptions: any = {
    // Your options
  };
}
