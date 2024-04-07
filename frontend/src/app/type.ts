export interface Task {
  id: number;
  name: string;
  description: string;
  deadline: Date;
  categoryId: number;
}

export interface Category {
  id: number;
  name: string;
  description: string;
  tasks: Task[];
}
