import { Pagination } from "./pagination.interface";
import { Article } from "./article.interface";

export interface Articles {
  data: Article[];
  pagination: Pagination;
}
