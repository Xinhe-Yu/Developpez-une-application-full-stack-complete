import { UserBasic } from "./userBasic.interface";
import { Topic } from "./topic.interface";

export interface Article {
  id: number;
  title: string;
  content: string;
  user: UserBasic;
  topic: Topic;
  createdAt?: string;
  updatedAt?: string;
}
