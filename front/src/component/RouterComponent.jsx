import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

import ListFilmeComponent from "./filme/ListFilmeComponent";
import AddFilmeComponent from "./filme/AddFilmeComponent";
import EditFilmeComponent from "./filme/EditFilmeComponent";

import ListLocacaoComponent from "./locacao/ListLocacaoComponent";
import AddLocacaoComponent from "./locacao/AddLocacaoComponent";
import EditLocacaoComponent from "./locacao/EditLocacaoComponent";

import ListUsuarioComponent from "./usuario/ListUsuarioComponent";
import AddUsuarioComponent from "./usuario/AddUsuarioComponent";
import EditUserComponent from "./usuario/EditUserComponent";

import ListHistoricoComponent from "./historico/ListHistoricoComponent";

import MainPage from "./MainPage";
import Profile from "./Profile";
import NotFound from "./NotFound";

import React from "react";
import LoginComponent from "./usuario/LoginComponent";

const AppRouter = () => {
    return (
        <Router>
            <Switch>
                <Route path="/" exact component={MainPage} />
                <Route path="/login" exact component={LoginComponent} />
                <Route path="/perfil" exact component={Profile} />
                
                <Route path="/lista-filmes" component={ListFilmeComponent} />
                <Route path="/add-filme" component={AddFilmeComponent} />
                <Route path="/editar-filme" component={EditFilmeComponent} />

                <Route path="/lista-locacoes" component={ListLocacaoComponent} />
                <Route path="/add-locacao" component={AddLocacaoComponent} />
                <Route path="/editar-locacao" component={EditLocacaoComponent} />

                <Route path="/lista-usuarios" component={ListUsuarioComponent} />
                <Route path="/add-usuario" component={AddUsuarioComponent} />
                <Route path="/editar-usuario" component={EditUserComponent} />

                <Route path="/lista-historico" component={ListHistoricoComponent} />

                <Route path="/notfound" exact component={NotFound} />
                <Route path='*' exact={true} component={NotFound} />

            </Switch>
        </Router>
    )
}

export default AppRouter;