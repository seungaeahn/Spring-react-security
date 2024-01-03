// App.js
import React, { useEffect, useState } from "react";
import axios from "axios";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import AddProduct from "./AddProduct";

function App() {
  const [products, setProducts] = useState([]);
  const [newProduct, setNewProduct] = useState({ name: "", price: 0 });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:8081/api/item");
        setProducts(response.data);
      } catch (error) {
        console.log("데이터를 불러오지 못했습니다.", error);
      }
    };
    fetchData();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewProduct((prevProduct) => ({ ...prevProduct, [name]: value }));
  };

  const handleAddProduct = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8081/api/add",
        newProduct,
        { withCredentials: true }
      );
      setProducts((prevProducts) => [...prevProducts, response.data]);
      setNewProduct({ name: "", price: 0 });
    } catch (error) {
      console.log("error:", error);
    }
  };

  return (
    <Router>
      <div>
        <h1>상품 리스트</h1>
        <ul>
          {products.map((product) => (
            <li key={product.id}>
              {product.name} - {product.price}원
            </li>
          ))}
          <li>
            <Link to="/add">상품 추가하기</Link>
          </li>
        </ul>

        <Routes>
          <Route
            path="/add"
            element={<AddProduct onAddProduct={handleAddProduct} />}
          />
          <Route path="/item" element={<h2>상품리스트</h2>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
