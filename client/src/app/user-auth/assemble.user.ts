export class AssembleUser {

    constructor(private email: string, private id: string, private authenticated: boolean) {
    }

    static noUser() {
        return new AssembleUser('', '', false);
    }

    getEmail(): string {
        return this.email;
    }

    isAuthenticated(): boolean {
        return this.authenticated;
    }


    getId(): string {
        return this.id;
    }


}
