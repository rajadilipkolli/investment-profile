export class Portfolio {
    public name: string;
    public type: string;
    public quantity: number;
    public costPrice: number;
    public currentPrice: number;
    public profitLossPercent: number;
    public profit: boolean;

    public constructor(name: string, type: string, quantity: number, costPrice: number, currentPrice: number, profitLossPercent: number, profit: boolean) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.currentPrice = currentPrice;
        this.profitLossPercent = profitLossPercent;
        this.profit = profit;
    }
}