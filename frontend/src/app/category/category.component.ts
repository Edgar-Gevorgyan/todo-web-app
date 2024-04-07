import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service';
import { Category, Task } from '../type';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TaskFormModalComponent } from './task-form-modal/task-form-modal.component';
import { TaskService } from '../services/task.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
})
export class CategoryComponent implements OnInit {
  category: Category | undefined;

  constructor(
    private taskService: TaskService,
    private categoryService: CategoryService,
    private modalService: NgbModal,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.fetchCategory();
  }

  openTaskModal(task?: Task) {
    const taskModalRef = this.modalService.open(TaskFormModalComponent, {
      centered: true,
    });
    taskModalRef.componentInstance.task = task;
    taskModalRef.componentInstance.categoryId = this.category!.id;

    taskModalRef.result.then(
      (result) => {
        if (task) {
          this.taskService
            .updateTask(task.id, result)
            .subscribe(() => this.fetchCategory());
        } else {
          this.taskService
            .createTask(result)
            .subscribe(() => this.fetchCategory());
        }
      },
      () => {}
    );
  }

  deleteTask(taskId: number) {
    this.taskService.deleteTask(taskId).subscribe(() => this.fetchCategory());
  }

  private fetchCategory() {
    const categoryId = this.route.snapshot.params['categoryId'];
    this.categoryService
      .getCategory(categoryId)
      .subscribe((category) => (this.category = category));
  }
}
