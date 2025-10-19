function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("token")
    };
}

let sales = [];

async function loadAllSales() {
    try {
        const response = await fetch("/sales", { headers: getAuthHeaders() });
        if (!response.ok) {
            const error = await response.json();
            alert("Erro: " + (error.error || JSON.stringify(error)));
            throw new Error("Erro ao carregar vendas: " + error);
        }
        sales = await response.json();
        renderSales(sales);
    } catch (err) {
        alert(err.message);
        console.error(err);
    }
}

function renderSales(list) {
    const tbody = document.querySelector("#salesTable tbody");
    tbody.innerHTML = "";
    list.forEach(sale => {
        sale.productsSold.forEach(p => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${sale.id}</td>
                <td>${new Date(sale.saleDate).toLocaleDateString("pt-BR")}</td>
                <td>${p.name}</td>
                <td>${Number(p.unitPrice || 0).toFixed(2)}</td>
                <td>${p.unitOfMeasure}</td>
                <td>${p.quantity}</td>
                <td>${(p.unitPrice * p.quantity).toFixed(2)}</td>
            `;
            tbody.appendChild(tr);
        });
    });
}

document.getElementById("btnFilter").addEventListener("click", async () => {
    const name = document.getElementById("productName").value;
    const category = document.getElementById("categoryFilter").value;
    const from = document.getElementById("fromDate").value;
    const to = document.getElementById("toDate").value;

    try {
        const params = new URLSearchParams();
        if (name) params.append("name", name);
        if (category) params.append("category", category);
        if (from) params.append("from", from);
        if (to) params.append("to", to);

        const response = await fetch(`/sales/filter?${params.toString()}`, {
            headers: getAuthHeaders()
        });
        if (!response.ok) {
            const error = await response.json();
            alert("Erro: " + (error.error || JSON.stringify(error)));
            throw new Error("Erro ao filtrar vendas: " + error);
        }
        const filtered = await response.json();

        if (filtered.length === 0) {
            alert("Nenhuma venda encontrada para os critérios especificados.");
        }
        renderSales(filtered);
    } catch (err) {
        alert(err.message);
        console.error(err);
    }
});

document.getElementById("btnShowAll").addEventListener("click", loadAllSales);

document.getElementById("btnExport").addEventListener("click", () => {
    const rows = [["ID Venda", "Data da Venda", "Nome do Produto", "Preço Unitário (R$)", "Unidade", "Quantidade", "Valor Total"]];
    document.querySelectorAll("#salesTable tbody tr").forEach(tr => {
        const cols = Array.from(tr.querySelectorAll("td")).map(td => td.textContent);
        rows.push(cols);
    });

    if (rows.length === 1) {
        alert("Não há dados para exportar.");
        return;
    }

    const csvContent = rows.map(e => e.join(",")).join("\n");
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = `vendas_${new Date().toISOString().replace(/[:.]/g, "-")}.csv`;
    link.click();
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
}

async function loadCategories() {
    try {
        const response = await fetch("/products/categories", { headers: getAuthHeaders() });
        if (!response.ok) {
            const error = await response.json();
            alert("Erro: " + (error.error || JSON.stringify(error)));
            throw new Error("Erro ao carregar categorias: " + error);
        }
        const categories = await response.json();

        const categorySelect = document.getElementById("categoryFilter");
        categorySelect.innerHTML = '<option value="Todas">Todas</option>';

        categories.forEach(cat => {
            const opt = document.createElement("option");
            opt.value = cat;
            opt.textContent = cat;
            categorySelect.appendChild(opt);
        });
    } catch (err) {
        console.error(err);
        alert("Erro ao carregar categorias.");
    }
}

loadAllSales();
loadCategories();
