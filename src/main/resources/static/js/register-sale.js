let products = [];
let cart = [];

function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("token")
    };
}

async function loadProducts() {
    const response = await fetch("/products", { headers: getAuthHeaders() });
    if (!response.ok) {
        const error = await response.json();
        alert("Erro: " + (error.error || JSON.stringify(error)));
        throw new Error("Erro ao carregar produtos");
    }
    products = await response.json();

    const tbody = document.querySelector("#productsTable tbody");
    tbody.innerHTML = "";
    products.forEach(p => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
    <td>${p.id}</td>
    <td>${p.name}</td>
    <td>${p.category}</td>
    <td>${Number(p.unitPrice || 0).toFixed(2)}</td>
    <td>${p.unitOfMeasure}</td>
    <td>${p.quantity}</td>
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

    const grouped = {};
    cart.forEach(item => {
        if (grouped[item.productId]) {
            grouped[item.productId].quantity += item.quantity;
        } else {
            grouped[item.productId] = { ...item };
        }
    });

    Object.values(grouped).forEach(item => {
        const product = products.find(p => p.id === item.productId);
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${item.productId}</td>
            <td>${product ? product.name : ""}</td>
            <td>${product ? product.category : ""}</td>
            <td>${item.quantity}</td>
            <td>${product ? Number(product.unitPrice || 0).toFixed(2) : "0.00"}</td>
            <td>${product ? product.unitOfMeasure : ""}</td>
        `;
        tr.addEventListener("click", () => {
            document.querySelectorAll("#cartTable tr").forEach(row => row.classList.remove("selected"));
            tr.classList.add("selected");
        });
        tbody.appendChild(tr);
    });
}

async function refreshCart() {
    const response = await fetch("/cart", { headers: getAuthHeaders() });
    if (response.ok) {
        cart = await response.json();
        renderCart();
    }
}

document.getElementById("btnAddProduct").addEventListener("click", async () => {
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

    const response = await fetch(`/cart/add?productId=${id}&qty=${qty}`, {
        method: "POST",
        headers: getAuthHeaders()
    });

    if (!response.ok) {
        const error = await response.json();
        alert("Erro: " + (error.error || JSON.stringify(error)));
        throw new Error("Erro ao adicionar produto ao carrinho: " + error);
    }

    cart = await response.json();
    renderCart();
    loadProducts();
    document.getElementById("productQuantity").value = "";
});

document.getElementById("btnRemoveItem").addEventListener("click", async () => {
    const selected = document.querySelector("#cartTable tr.selected");
    if (!selected) {
        alert("Selecione um item para remover.");
        return;
    }
    const id = parseInt(selected.cells[0].textContent);

    const response = await fetch(`/cart/remove?productId=${id}`, {
        method: "POST",
        headers: getAuthHeaders()
    });

    if (!response.ok) {
        const error = await response.json();
        alert("Erro: " + (error.error || JSON.stringify(error)));
        throw new Error("Erro ao remover item do carrinho: " + error);
    }

    cart = await response.json();
    renderCart();
    loadProducts();
});

document.getElementById("btnCalculateTotal").addEventListener("click", async () => {
    const response = await fetch("/sales/calculate-total", {
        method: "POST",
        headers: getAuthHeaders(),
        body: JSON.stringify(cart.map(item => {
            const product = products.find(p => p.id === item.productId);
            return {
                productId: item.productId,
                name: product ? product.name : "",
                category: product ? product.category : "",
                unitPrice: product ? product.unitPrice : 0,
                unitOfMeasure: product ? product.unitOfMeasure : "",
                quantity: item.quantity
            };
        }))
    });

    if (!response.ok) {
        const error = await response.json();
        alert("Erro: " + (error.error || JSON.stringify(error)));
        return;
    }

    const total = await response.json();
    document.getElementById("totalValue").textContent = `Valor Total: R$ ${total.toFixed(2)}`;
});

document.getElementById("btnClearCart").addEventListener("click", async () => {
    const response = await fetch("/cart/clear", {
        method: "POST",
        headers: getAuthHeaders()
    });

    if (!response.ok) {
        const error = await response.json();
        alert("Erro: " + (error.error || JSON.stringify(error)));
        throw new Error("Erro ao limpar carrinho: " + error);
    }

    cart = await response.json();
    renderCart();
    loadProducts();
    document.getElementById("totalValue").textContent = "Valor Total: R$ 0,00";
});

document.getElementById("btnFinalizeSale").addEventListener("click", async () => {
    if (cart.length === 0) {
        alert("Carrinho vazio.");
        return;
    }

    const cartDTO = {
        items: cart.map(item => ({
            productId: item.productId,
            quantity: item.quantity
        }))
    };

    const response = await fetch("/sales", {
        method: "POST",
        headers: getAuthHeaders(),
        body: JSON.stringify(cartDTO)
    });

    if (!response.ok) {
        const error = await response.json();
        alert("Erro: " + (error.error || JSON.stringify(error)));
        throw new Error("Erro ao finalizar a venda: " + error);
    }

    const sale = await response.json();
    alert(`Venda finalizada com sucesso! Código da venda: ${sale.id}`);

    await fetch("/cart/clear", {
        method: "POST",
        headers: getAuthHeaders()
    });
    await refreshCart();
    loadProducts();
    document.getElementById("totalValue").textContent = "Valor Total: R$ 0,00";
});

document.getElementById("menuLeave").addEventListener("click", () => {
    if (confirm("Deseja sair?")) {
        localStorage.clear();
        window.location.href = "../login.html";
    }
});

const role = localStorage.getItem("role");
if (role === "EMPLOYEE") {
    document.getElementById("menuRegisterProduct").style.pointerEvents = "none";
    document.getElementById("menuRegisterProduct").style.opacity = "0.5";
    document.getElementById("menuEditProduct").style.pointerEvents = "none";
    document.getElementById("menuEditProduct").style.opacity = "0.5";
    document.getElementById("menuSalesReport").style.pointerEvents = "none";
    document.getElementById("menuSalesReport").style.opacity = "0.5";
}

loadProducts();
refreshCart();
