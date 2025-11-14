import logo from './logo.png';
import video_banner from './home-page-banner.mp4';
import people from './people.png';
import people_org from './people-org.png';
import credits from './dollar.png';

export const assets = {
  logo,
  video_banner,
  people,
  people_org,
  credits,
};

export const steps = [
  {
    step: "Step 1",
    title: "Select an Image",
    description: [
      "Choose the image you want to remove the background from by clicking on 'Start from a photo'.",
      "Your image format can be PNG or JPG.",
      "We support all dimensions."
    ]
  },
  {
    step: "Step 2",
    title: "Let the AI remove the background",
    description: [
      "Our tool automatically removes the background from your image.",
      "Next, choose a background color.",
      "Popular options are white and transparent, but you can pick any color."
    ]
  },
  {
    step: "Step 3",
    title: "Download Your Image",
    description: [
      "After selecting a new background color, the background will be removed.",
      "Download your photo — and you're done!",
      "You can also save your photo in the PhotoRoom App by creating an account."
    ]
  }
];

export const categories = ["People", "Products", "Animals", "Cars", "Graphics"];


export const plans = [
  {
    id: "Basic",
    name: "Basic Package",
    price: 499,
    credits: "100 credits",
    description: "Best for personal use",
    popular: false
  },
  {
    id: "Premium",
    name: "Premium Package",
    price: 899,
    credits: "250 credits",
    description: "Best for business use",
    popular: true
  },
  {
    id: "Ultimate",
    name: "Ultimate Package",
    price: 1499,
    credits: "1000 credits",
    description: "Best for enterprise use",
    popular: false
  }
];

export const testimonials = [
  {
    id: 1,
    quote: "We are impressed by the AI and think it's the best choice on the market.",
    author: "Anthony Walker",
    handle: "@_webarchitect"
  },
  {
    id: 2,
    quote: "DropBG is leaps and bounds ahead of the competition. A thousand times better. It simplified the whole process.",
    author: "Sarah Johnson",
    handle: "@techlead-sarah"
  },
  {
    id: 3,
    quote: "We are impressed by the ability to account for pesky, feathery hair without making an image looked jagged and amateriush.",
    author: "Michael Chen",
    handle: "@coding_newbie"
  },
];


export const FOOTER_CONSTANTS = [
  {
    url: "https://facebook.com",
    logo: "https://png.pngtree.com/png-clipart/20230401/original/pngtree-facebook-icon-social-media-logo-png-image_9015413.png"
  },
  {
    url: "https://linkedin.com",
    logo: "https://pngimg.com/uploads/linkedIn/linkedIn_PNG8.png"
  },
  {
    url: "https://instagram.com",
    logo: "https://blog.logomyway.com/wp-content/uploads/2022/01/Instagram_logo.png"
  },
  {
    url: "https://twitter.com",
    logo: "https://static.vecteezy.com/system/resources/previews/027/395/710/non_2x/twitter-brand-new-logo-3-d-with-new-x-shaped-graphic-of-the-world-s-most-popular-social-media-free-png.png"
  },
];