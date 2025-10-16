let products = [
    { id: 1, name: "Produto A", category: "Bebidas", price: 10.5, unit: "Unidade", stock: 50 },
    { id: 2, name: "Produto B", category: "Alimentos", price: 5.0, unit: "Kg", stock: 30 }
];
let originalStock = JSON.parse(JSON.stringify(products));
let cart = [];

function loadProducts() {
    const tbody = document.querySelector("#productsTable tbody");
    tbody.innerHTML = "";
    products.forEach(p => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
      <td>${p.id}</td>
      <td>${p.name}</td>
      <td>${p.category}</td>
      <td>${p.price.toFixed(2)}</td>
      <td>${p.unit}</td>
      <td>${p.stock}</td>
    `;
        tr.addEventListener("click", () => {
            document.querySelectorAll("#productsTable tr").forEach(row => row.classList.remove("selected"));
            tr.classList.add("selected");
        });
        tbody.appendChild(tr);
    });
}

function renderCart() {
    const tbody = document.querySelector("#cartTable tbody");
    tbody.innerHTML = "";
    cart.forEach(item => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
      <td>${item.id}</td>
      <td>${item.name}</td>
      <td>${item.category}</td>
      <td>${item.qty}</td>
      <td>${item.price.toFixed(2)}</td>
      <td>${item.unit}</td>
    `;
        tr.addEventListener("click", () => {
            document.querySelectorAll("#cartTable tr").forEach(row => row.classList.remove("selected"));
            tr.classList.add("selected");
        });
        tbody.appendChild(tr);
    });
}

document.getElementById("btnAddProduct").addEventListener("click", () => {
    const selected = document.querySelector("#productsTable tr.selected");
    if (!selected) {
        alert("Selecione um produto.");
        return;
    }
    const id = parseInt(selected.cells[0].textContent);
    const qty = parseInt(document.getElementById("productQuantity").value);
    if (!qty || qty <= 0) {
        alert("Quantidade inválida.");
        return;
    }

    const product = products.find(p => p.id === id);
    if (qty > product.stock) {
        alert("Quantidade solicitada maior que o estoque disponível.");
        return;
    }

    product.stock -= qty;

    const existing = cart.find(c => c.id === id);
    if (existing) {
        existing.qty += qty;
    } else {
        cart.push({ ...product, qty });
    }

    renderCart();
    loadProducts();
    document.getElementById("productQuantity").value = "";
});

document.getElementById("btnRemoveItem").addEventListener("click", () => {
    const selected = document.querySelector("#cartTable tr.selected");
    if (!selected) {
        alert("Selecione um item para remover.");
        return;
    }
    const id = parseInt(selected.cells[0].textContent);
    const item = cart.find(c => c.id === id);

    const product = products.find(p => p.id === id);
    product.stock += item.qty;

    cart = cart.filter(c => c.id !== id);
    renderCart();
    loadProducts();
});

document.getElementById("btnCalculateTotal").addEventListener("click", () => {
    const total = cart.reduce((sum, item) => sum + item.qty * item.price, 0);
    document.getElementById("totalValue").textContent = `Valor Total: R$ ${total.toFixed(2)}`;
});

document.getElementById("btnClearCart").addEventListener("click", () => {
    products = JSON.parse(JSON.stringify(originalStock));
    cart = [];
    renderCart();
    loadProducts();
    document.getElementById("totalValue").textContent = "Valor Total: R$ 0,00";
});

document.getElementById("btnFinalizeSale").addEventListener("click", () => {
    if (cart.length === 0) {
        alert("Carrinho vazio.");
        return;
    }
    alert("Venda finalizada com sucesso!");
    cart = [];
    renderCart();
    loadProducts();
    document.getElementById("totalValue").textContent = "Valor Total: R$ 0,00";
    originalStock = JSON.parse(JSON.stringify(products));
});

document.getElementById("menuLeave").addEventListener("click", () => {
    if (confirm("Deseja sair?")) {
        window.location.href = "../login/index.html";
    }
});

const currentUser = { userType: "ADMIN" };
if (currentUser.userType === "EMPLOYEE") {
    document.getElementById("menuRegisterProduct").style.pointerEvents = "none";
    document.getElementById("menuRegisterProduct").style.opacity = "0.5";
    document.getElementById("menuEditProduct").style.pointerEvents = "none";
    document.getElementById("menuEditProduct").style.opacity = "0.5";
    document.getElementById("menuSalesReport").style.pointerEvents = "none";
    document.getElementById("menuSalesReport").style.opacity = "0.5";
}

loadProducts();
renderCart();
