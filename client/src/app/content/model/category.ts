export class Category {

    categoryName: string;
    parentCategoryName: string;
    subCategories: Category[];
    isFinal: boolean;


    constructor(categoryName: string, parentCategoryName: string, subCategories: Category[], isFinal: boolean) {
        this.categoryName = categoryName;
        this.parentCategoryName = parentCategoryName;
        this.subCategories = subCategories;
        this.isFinal = isFinal;
    }

    static emptyCategory() {
        return new Category('', '', [], true);
    }
}
