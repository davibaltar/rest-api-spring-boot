import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import { Link } from "react-router-dom";
import AuthService from '../service/AuthService';

class HomePage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            message: '',
        }

    }

    componentDidMount() {
        // localStorage.clear();
        //let test = AuthService.getUserInfo();
    }

    render() {

        const space = {
            paddingRight: 10,
            paddingLeft: 10
        }

        return (
            <React.Fragment>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6">
                            Locadora
                        </Typography>
                        <Typography style={space}>|</Typography>
                        {AuthService.getUserInfo() ? <Button color="inherit" component={Link} to="/perfil">Meu Perfil</Button> : <Button color="inherit" component={Link} to="/login">Login</Button>}
                    </Toolbar>
                </AppBar>

                <Typography variant="h4" style={styles.userTitle}>MAIN PAGE</Typography>

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
        color: '#b36161',
        fontSize: 14,
        paddingTop: 5
    },
    userTitle: {
        display: 'flex',
        justifyContent: 'center',
        color: '#b36161',
        fontSize: 14,
        marginTop: 100
    }
}


export default HomePage;