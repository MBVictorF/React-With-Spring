import React from 'react';
import './App.css';
import Store from './Store';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProductList from './ProductList';
import ProductEdit from './ProductEdit';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Store/>}/>
                <Route path='/products' exact={true} element={<ProductList/>}/>
                <Route path='/products/:id' element={<ProductEdit/>}/>
            </Routes>
        </Router>
    )
}

export default App;