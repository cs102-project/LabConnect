import React from 'react';
import '../scss/login.scss';

function LoginForm(): JSX.Element {
    return (
        <form id="login-form" name="login-form" action="login" method="POST">
            <h2>Login</h2>

            {new URLSearchParams(window.location.search).has('error') && (
                <p className="error-block">The given credentials could not be authenticated.</p>
            )}

            <label htmlFor="username">Email</label>
            <input className="hollow-input" type="text" name="username" id="username" placeholder="email@example.com" />
            <label htmlFor="password">Password</label>
            <input className="hollow-input" type="password" name="password" id="password" placeholder="********" />
            <input name="submit" type="submit" value="Login" className="button" />
            <input
                type="hidden"
                name="_csrf"
                value={
                    document.cookie
                        .split('; ')
                        .find((row) => row.startsWith('XSRF-TOKEN='))
                        ?.split('=')[1]
                }
            />
        </form>
    );
}

export default LoginForm;
