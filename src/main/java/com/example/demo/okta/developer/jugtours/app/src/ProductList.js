import React, { useEffect, useState } from 'react';
import { Button, Container, Card, CardBody, CardTitle, CardText, CardSubtitle, CardImg } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import './ProductList.css'; // Добавим новый CSS-файл

const ProductList = () => {

    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch('/api/products')
            .then(response => response.json())
            .then(data => {
                setProducts(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching products:', error);
                setProducts([]);
                setLoading(false);
            });
    }, []);

    const remove = async (id) => {
        await fetch(`/api/product/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedProducts = [...products].filter(i => i.id !== id);
            setProducts(updatedProducts);
        });
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (!products || products.length === 0) {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-end">
                        <Button color="success" tag={Link} to="/products/new">Add Product</Button>
                    </div>
                    <h3>My Store</h3>
                    <p>No products found.</p>
                </Container>
            </div>
        );
    }

    // Преобразуем массив товаров в массив карточек
    const productCards = products.map(product => {
        return (
            <Card key={product.id} className="product-card">
                <CardImg top width="100%" src={product.imageUrl} alt={product.name} />
                <CardBody>
                    <CardTitle tag="h5">{product.name}</CardTitle>
                    <CardSubtitle tag="h6" className="mb-2 text-muted">{product.price} eur.</CardSubtitle>
                    <CardText>{product.description}</CardText>
                    {/* Кнопка "Добавить в корзину" (пока неактивна) */}
                    <Button color="primary" className="me-2">Add to Cart</Button>
                    {/* Кнопки "Редактировать" и "Удалить" для админки */}
                    <Button size="sm" color="info" tag={Link} to={"/products/" + product.id} className="me-2">Edit</Button>
                    <Button size="sm" color="danger" onClick={() => remove(product.id)}>Delete</Button>
                </CardBody>
            </Card>
        );
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="d-flex justify-content-between align-items-center mb-4">
                    <h3>My Store</h3>
                    <Button color="success" tag={Link} to="/products/new">Add Product</Button>
                </div>
                {/* Используем Flexbox для выстраивания карточек */}
                <div className="d-flex flex-wrap justify-content-center product-container">
                    {productCards}
                </div>
            </Container>
        </div>
    );
};

export default ProductList;