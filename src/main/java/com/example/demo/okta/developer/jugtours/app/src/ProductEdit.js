import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

const ProductEdit = () => {
    // 1. Обновим начальное состояние, чтобы оно соответствовало полям Product
    const initialFormState = {
        name: '',
        description: '',
        price: '',
        imageUrl: ''
    };

    const [product, setProduct] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id !== 'new') {
            fetch(`/api/product/${id}`)
                .then(response => response.json())
                .then(data => setProduct(data));
        }
    }, [id, setProduct]);

    const handleChange = (event) => {
        const { name, value } = event.target;

        // Обработка цены как числа
        if (name === 'price') {
            setProduct({ ...product, [name]: parseFloat(value) || 0 });
        } else {
            setProduct({ ...product, [name]: value });
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        await fetch(`/api/product${product.id ? `/${product.id}` : ''}`, {
            method: (product.id) ? 'PUT' : 'POST', // 2. Исправлена переменная group -> product
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        });
        setProduct(initialFormState);
        navigate('/products'); // 3. Исправлен путь /groups -> /products
    };

    const title = <h2>{product.id ? 'Edit Product' : 'Add Product'}</h2>;

    return (
        <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={product.name || ''}
                               onChange={handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={product.description || ''}
                               onChange={handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Label for="price">Price</Label>
                        <Input type="number" name="price" id="price" value={product.price || ''}
                               onChange={handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Label for="imageUrl">Image URL</Label>
                        <Input type="text" name="imageUrl" id="imageUrl" value={product.imageUrl || ''}
                               onChange={handleChange} />
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/products">Cancel</Button> {/* 4. Исправлен путь /groups -> /products */}
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
};

export default ProductEdit;