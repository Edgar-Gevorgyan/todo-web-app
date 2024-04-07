import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Task } from 'src/app/type';

@Component({
  selector: 'app-task-form-modal',
  templateUrl: './task-form-modal.component.html',
  styleUrls: ['./task-form-modal.component.scss'],
})
export class TaskFormModalComponent implements OnInit {
  @Input() task?: Task;
  @Input() categoryId?: number;

  taskForm: FormGroup;
  closeButtonClicked = false;

  constructor(
    private activeModal: NgbActiveModal,
    private formBuilder: FormBuilder
  ) {
    this.taskForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: [''],
      deadline: [new Date(Date.now()).toISOString(), [Validators.required]],
      categoryId: [1, [Validators.required]],
    });
  }

  get name() {
    return this.taskForm.get('name')!;
  }

  get deadline() {
    return this.taskForm.get('deadline')!;
  }

  ngOnInit(): void {
    this.taskForm = this.formBuilder.group({
      name: [this.task?.name || '', [Validators.required]],
      description: [this.task?.description || ''],
      deadline: [
        this.task?.deadline || new Date(Date.now()).toISOString(),
        [Validators.required],
      ],
      categoryId: [this.categoryId || 1, [Validators.required]],
    });
  }

  onDismiss(reason?: string): void {
    this.activeModal.dismiss(reason);
  }

  onClose() {
    this.closeButtonClicked = true;
    if (this.taskForm.valid) {
      this.activeModal.close(this.taskForm.value);
    }
  }
}
