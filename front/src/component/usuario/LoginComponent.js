import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import AuthService from '../../service/AuthService';
import InputMask from 'react-input-mask'

class LoginComponent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            message: '',
        }
        this.login = this.login.bind(this);
    }

    componentDidMount() {
        localStorage.clear();
    }

    login = (e) => {
        e.preventDefault();
        const credentials = { cpf: this.state.username.split('.').join('').split('-').join(''), senha: this.state.password };
        AuthService.login(credentials).then(res => {
            if (res.status === 200) {
                localStorage.setItem("userInfo", JSON.stringify({ token: res.data, username: "Usuario" })); // Nome do usuario
                this.props.history.push('/lista-locacoes');
            } else {
                this.setState({ message: "Usuario ou Senha inválida!" });
            }
        });
    };

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

    render() {

        return (
            <React.Fragment>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6">
                            Locadora
                        </Typography>
                    </Toolbar>
                </AppBar>
                <Container maxWidth="sm">
                    <Typography variant="h4" style={styles.center}>Login</Typography>
                    <form>
                        <Typography variant="h4" style={styles.notification}>{this.state.message}</Typography>
                        {/* <TextField type="text" label="CPF" fullWidth margin="normal" name="username" value={this.state.username} onChange={this.onChange} /> */}
                        <InputMask
                            mask="999.999.999-99"
                            value={this.state.username}
                            onChange={this.onChange}
                        >
                            {() => <TextField type="text" label="CPF" fullWidth margin="normal" name="username" />}
                        </InputMask>

                        <TextField type="password" label="SENHA" fullWidth margin="normal" name="password" value={this.state.password} onChange={this.onChange} />

                        <Button variant="contained" color="secondary" onClick={this.login}>Login</Button>
                    </form>
                </Container>
                <Typography variant="h4" style={styles.userTitle}>USUARIO PARA TESTE</Typography>
                <Typography variant="h4" style={styles.user}>CPF: 000.000.000-00 </Typography>
                <Typography variant="h4" style={styles.user}>SENHA: 123456 </Typography>
            </React.Fragment>
        )
    }
}

const styles = {
    center: {
        display: 'flex',
        justifyContent: 'center',
        marginTop: 15
    },
    notification: {
        display: 'flex',
        justifyContent: 'center',
        color: '#dc3545'
    },
    user: {
        display: 'flex',
        justifyContent: 'center',
        color: 'red',
        fontSize: 14,
        paddingTop: 5
    },
    userTitle: {
        display: 'flex',
        justifyContent: 'center',
        color: 'red',
        fontSize: 14,
        marginTop: 100
    }
}


export default LoginComponent;