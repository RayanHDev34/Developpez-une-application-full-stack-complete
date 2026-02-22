import { Article } from 'src/app/interfaces/articles.interface';

export interface ArticleDetailResponse {
  article: Article;
  comments: Comment[];
}

export interface Comment {
  id: number;
  content: string;
  createdAt: string;
  authorName: string;
}
