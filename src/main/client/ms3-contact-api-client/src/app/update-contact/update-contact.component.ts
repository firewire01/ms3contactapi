import { Component, OnInit } from '@angular/core';
import { Contact } from '../contact-component/contact';
import { ActivatedRoute, Router } from '@angular/router';
import { ContactService } from '../contact-component/contact.service';
import { Address } from '../contact-component/address';
import { Communication } from '../contact-component/communication';
import * as moment from 'moment';
import { Gender } from '../create-contact/create-contact.component';
import { Option } from '../create-contact/create-contact.component';
import { Observable, BehaviorSubject } from 'rxjs';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.css']
})
export class UpdateContactComponent implements OnInit {

  id: number;
  contact: Contact = new Contact();
  submitted = false;
  addresses: Address[] = [];
  comms: Communication[] = [];
  add : Address = new Address();
  com : Communication = new Communication();
  date : Date = new Date();
  datepipe : DatePipe;
  errorMessage: string = "";

  genders: Gender[] = [
    { value: 'M', viewValue: 'Male' },
    { value: 'F', viewValue: 'Female' }
  ];

  options: Option[] = [
    { value: false , viewValue: 'False' },
    { value: true, viewValue: 'True' }

  ];


  constructor(private route: ActivatedRoute,private router: Router,
    private contactService: ContactService, public datePipe: DatePipe) {
    this.datepipe =  datePipe;
    this.contact.Identification = {
      id: 0,
      firstName: '',
      lastName: '',
      dob: '',
      gender: '',
      title: ''
    }

    this.reload();

  }

  reload(){
      this.id = this.route.snapshot.params['id'];

          this.contactService.getContact(this.id)
            .subscribe(data => {
              console.log(data)
              this.contact = data;

              for (let i = 0; i < this.contact.Communication.length; i++) {
                let preferred = this.contact.Communication[i].preferred;
                if (preferred == null || preferred == undefined) {
                  this.contact.Communication[i].preferred = false;
                }
              }

              this.addresses = this.contact.Address;
              this.comms = this.contact.Communication;

              this.date = new Date(this.contact.Identification.dob);

              this.contact.Address = [];
              this.contact.Communication = [];

            }, error => {console.log(error);
                               alert(error.message);});
  }

  ngOnInit() {

  }

  updateContact() {
    this.contact.Identification.dob = this.datepipe.transform(this.date, 'MM/dd/yyyy');

    this.contact.Address = this.addresses;
    this.contact.Communication = this.comms;

    console.log(this.contact);

    this.contactService.updateContact(this.contact)
      .subscribe(data => {
        console.log(data);
        this.contact = new Contact();
        this.gotoList();
        this.submitted = true;
      }, error => {console.log(error);
                         alert(error.message);
                         this.submitted = false});
  }



  onSubmit() {

    if(this.add.type && this.add.street && this.add.city){
       this.addAddress();
    }

    if(this.com.type && this.com.value){
        this.addComm();
    }

    if(this.validForm()){
       this.updateContact();
    }else{
      alert(this.errorMessage);
      this.errorMessage = "";
    }
  }

  validForm() : boolean {

     let result = true;

     if(this.addresses.length < 1){
        this.errorMessage += "Address is required please add either one input. ";
        result = false;
     }

     if(this.comms.length < 1){
        this.errorMessage += "Communication is required please add either one input. ";
        result = false;
     }

     return result;
  }

  isDate(dateStr) {
    return !isNaN(new Date(dateStr).getDate());
  }

  reset() {
      this.reload();
  }

  addAddress() {
      this.add.id = 0;
      this.addresses.push(this.add);
      this.add = new Address();
    }

    addComm() {
      this.com.id = 0;
      this.comms.push(this.com);
      this.com = new Communication();
    }

    deleteAdd(index){
      this.addresses.splice(index, 1);
    }

    deleteComm(index){
        this.comms.splice(index, 1);
    }

  gotoList() {
    this.router.navigate(['/contacts']);
  }
}
