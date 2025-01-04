import { UserBasic } from "./userBasic.interface";
import { Topic } from "./topic.interface";

export interface Article {
  id: number;
  title: string;
  content: string;
  user: UserBasic;
  topic: Topic;
  created_at?: string;
  updated_at?: string;
}
