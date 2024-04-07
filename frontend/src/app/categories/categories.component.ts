import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service';
import { Category } from '../type';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CategoryFormModalComponent } from './category-form-modal/category-form-modal.component';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss'],
})
export class CategoriesComponent implements OnInit {
  categories: Category[] = [];

  constructor(
    private categoryService: CategoryService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.fetchCategories();
  }

  openCategoryModal(category?: Category) {
    const categoryModalRef = this.modalService.open(
      CategoryFormModalComponent,
      {
        centered: true,
      }
    );
    categoryModalRef.componentInstance.category = category;
    categoryModalRef.result.then(
      (result) => {
        if (category) {
          this.categoryService
            .updateCategory(category.id, result)
            .subscribe(() => this.fetchCategories());
        } else {
          this.categoryService
            .createCategory(result)
            .subscribe(() => this.fetchCategories());
        }
      },
      () => {}
    );
  }

  deleteCategory(categoryId: number) {
    this.categoryService
      .deleteCategory(categoryId)
      .subscribe(() => this.fetchCategories());
  }

  private fetchCategories() {
    this.categoryService
      .getCategories()
      .subscribe((categories) => (this.categories = categories));
  }
}
