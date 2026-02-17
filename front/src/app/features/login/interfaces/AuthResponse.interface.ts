import { User } from "src/app/interfaces/user.interface";

export interface AuthResponse {
  token: string;
  user: User;
}
