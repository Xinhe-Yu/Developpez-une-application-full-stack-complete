import { UserBasic } from "./userBasic.interface";

export interface Comment {
  id: number;
  content: string;
  user: UserBasic;
  createdAt: string;
  updatedAt?: string;
}
