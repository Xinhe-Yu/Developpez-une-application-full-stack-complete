import { Comment } from "./comment.interface";
import { Pagination } from "./pagination.interface";

export interface Comments {
  data: Comment[];
  pagination: Pagination;
}
