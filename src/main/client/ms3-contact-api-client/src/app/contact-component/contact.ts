import { Address } from './address';
import { Communication } from './communication';
import { Identification } from './identification';

export class Contact {
  Identification: Identification;
  Address: Array<Address>;
  Communication: Array<Communication>;
}
