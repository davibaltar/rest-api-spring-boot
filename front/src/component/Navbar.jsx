import React from 'react'
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import { Link } from "react-router-dom";
import AuthService from "../service/AuthService";

const style = {
    flexGrow: 1
}

const space = {
    paddingRight: 10,
    paddingLeft: 10
}

const NavBar = () => {
    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" style={style}>
                        Locadora
                    </Typography>
                    {/* <Button color="inherit">{AuthService.getUserInfo().username}</Button> */}
                    <Button color="inherit" component={Link} to="/lista-locacoes">Locações</Button>
                    <Button color="inherit" component={Link} to="/lista-filmes">Filmes</Button>
                    <Button color="inherit" component={Link} to="/lista-usuarios">Usuários</Button>
                    <Button color="inherit" component={Link} to="/lista-historico">Histórico</Button>
                    <Typography style={space}>|</Typography>
                    <Button color="inherit" component={Link} to="/perfil">Perfil</Button>
                    <Typography style={space}>|</Typography>
                    <Button color="inherit" component={Link} onClick={AuthService.logOut} to="/">Logout</Button>
                </Toolbar>
            </AppBar>
        </div>
    )
}

export default NavBar;
