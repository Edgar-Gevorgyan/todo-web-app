import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Category } from 'src/app/type';

@Component({
  selector: 'app-category-form-modal',
  templateUrl: './category-form-modal.component.html',
  styleUrls: ['./category-form-modal.component.scss'],
})
export class CategoryFormModalComponent implements OnInit {
  @Input() category?: Category;

  categoryForm: FormGroup;
  closeButtonClicked = false;

  constructor(
    private activeModal: NgbActiveModal,
    private formBuilder: FormBuilder
  ) {
    this.categoryForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: [''],
    });
  }

  get name() {
    return this.categoryForm.get('name')!;
  }

  ngOnInit(): void {
    this.categoryForm = this.formBuilder.group({
      name: [this.category?.name || '', [Validators.required]],
      description: [this.category?.description || ''],
    });
  }

  onDismiss(reason?: string): void {
    this.activeModal.dismiss(reason);
  }

  onClose() {
    this.closeButtonClicked = true;
    if (this.categoryForm.valid) {
      this.activeModal.close(this.categoryForm.value);
    }
  }
}
