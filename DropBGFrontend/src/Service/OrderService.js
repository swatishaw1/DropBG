import axios from "axios";
import toast from "react-hot-toast";

export const placeOrder = async ({ planId, getToken, onSuccess, backendUrl }) => {
    try {
        const token = await getToken();
        const response = await axios.post(`${backendUrl}/orders?planId=${planId}`, {}, { headers: { Authorization: `Bearer ${token}` } });

        if (response.status === 200 || response.status === 201) {
            initializePayment({ order: response.data.data, getToken, onSuccess, backendUrl });
        }
    } catch (error) {
        toast.error(error.message);
    }
}

const initializePayment = async ({ order, getToken, onSuccess, backendUrl }) => {
    if (!window.Razorpay) {
        toast.error("Razorpay SDK not loaded");
        return;
    }

    //From https://razorpay.com/docs/payments/payment-gateway/web-integration/standard/integration-steps#1-build-integration
    const options = {
        key: import.meta.env.VITE_RAZORPAY_API_Key,
        amount: order.amount,
        currency: order.currency,
        name: "Credit Payment",
        description: "Credit Payment",
        order_id: order.id,
        receipt: order.receipt,
        handler: async (paymentDetails) => {
            try {
                const token = await getToken();
                const response = await axios.post(`${backendUrl}/orders/verify`, paymentDetails, { headers: { Authorization: `Bearer ${token}` } });
                if (response.status === 200) {
                    toast.success("Credits Added");
                    onSuccess?.();
                }
            } catch (error) {
                toast.error(error.message);
            }
        }
    }
    const rzp = new window.Razorpay(options);
    rzp.open();
}
