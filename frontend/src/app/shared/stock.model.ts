export class Stock {
    public name: string;
    public investmentType: string;
    public currentPrice: number;
    public anticipatedGrowth: number;
    public term: number;
    public quantity: number;

    public constructor(name: string, investmentType: string, currentPrice: number, anticipatedGrowth: number, term: number, quantity: number) {
        this.name = name;
        this.investmentType = investmentType;
        this.currentPrice = currentPrice;
        this.anticipatedGrowth = anticipatedGrowth;
        this.term = term;
        this.quantity = quantity;
    }
}
