import {AssembleUser} from '../../assemble.user';

export class UserAuthenticationAttempt {

    constructor(private authenticated: boolean, private user: AssembleUser, private token: string) {
        if (!authenticated) {
            this.user = AssembleUser.noUser();
        }
    }

    getToken(): string {
        return this.token;
    }

    getUser(): AssembleUser {
        return this.user;
    }

    isAuthenticated(): boolean {
        return this.authenticated;
    }
}
